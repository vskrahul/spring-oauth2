package com.github.vskrahul.authserver.config;

import java.security.KeyPair;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

@Configuration
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private TokenStore inMemoryTokenStore;
	
	@Autowired
	private KeyStore keyStore;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(); 
    }
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
					.withClient("twitter")
					.secret(passwordEncoder().encode("123456"))
					.authorizedGrantTypes("client_credentials")
					.authorities(JwtConstants.authorities)
					.scopes(JwtConstants.scopes)
					.resourceIds("oauth2-resource")
					//.redirectUris("http://localhost:8080/login")
					.accessTokenValiditySeconds(120)
					.refreshTokenValiditySeconds(240000);
	}
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer)
			throws Exception {
		oauthServer.tokenKeyAccess("permitAll()")
					.checkTokenAccess("isAuthenticated()");//allowFormAuthenticationForClients();
	}
	
	@Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
        	.accessTokenConverter(jwtAccessTokenConverter())
            .tokenStore(tokenStore())
            .userDetailsService(userDetailsService);
    }
	
	@Bean
	public TokenStore inMemoryTokenStore() {
		return new InMemoryTokenStore();
	}
	
	
	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(jwtAccessTokenConverter());
	}
	
	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		KeyPair keyPair = new KeyStoreKeyFactory(new ClassPathResource(keyStore.getFile())
																, keyStore.getStorepass().toCharArray())
							.getKeyPair(keyStore.getKeyAlias(), keyStore.getKeySecret().toCharArray());
		converter.setKeyPair(keyPair);
		return converter;
	}
}
