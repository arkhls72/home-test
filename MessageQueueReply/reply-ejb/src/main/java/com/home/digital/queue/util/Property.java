

package com.home.digital.queue.util;

/**
 * key value pair mapping to ejb.properties
 * @author arash
 */
public enum Property {
	DP_SERVER_URI("datapower.server.uri"),
	DP_Gain_URI("datapower.Gain.uri"),
	
	JBOSS_JNDI_PATH("jndi.uri.path"),
	INTERNAL_Gain_PUBLISH_QUEUE("internal.Gain.publish.queue"),
	
	HORNET_CONNECTION("hornetq.connection"),
	HORNET_USER("hornetq.user"),
	HORNET_PASSWORD("hornetq.password");

	private String code;
	private Property(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}
	
	public static Property pasre(String code) {
		for (Property item:Property.values()) {
			if (item.getCode().equals(code)) {
				return item;
			}
		}
		return null;
	}
}