package com.github.vskrahul.authserver.config;

public class JwtConstants {

	public static final String[] authorities = {
													"ROLE_ACTIVATION_API", "ROLE_BPS_QSD_SERVICE",
												    "ROLE_SPECTRUM_VIDEO_SERVICE", "ROLE_ACCOUNTS_SERVICE",
												    "ROLE_SERVICES_SERVICE", "ROLE_DEVICE_INFO_SERVICE",
												    "ROLE_SPECTRUM_PPV_SERVICE", "ROLE_AUTO_ACTIVATION_SERVICE",
												    "ROLE_SPECTRUM_EIPS_SERVICE", "ROLE_DEVICE_SERVICE",
												    "ROLE_NOTIFICATION_SERVICE"
												 };
	
	public static final String[] scopes = {"read", "write"};
}
