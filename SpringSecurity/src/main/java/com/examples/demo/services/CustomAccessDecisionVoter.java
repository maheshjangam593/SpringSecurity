package com.examples.demo.services;

import java.util.Collection;
import org.apache.commons.lang3.StringUtils;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class CustomAccessDecisionVoter implements AccessDecisionVoter<Object> {

	@Override
	public boolean supports(ConfigAttribute attribute) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
		// TODO Auto-generated method stub

		String anonymousName = "anonymous";

		final String username = (authentication.getPrincipal() == null) ? "NONE_PROVIDED" : authentication.getName();

		if (StringUtils.containsIgnoreCase(username, anonymousName)) {
			return ACCESS_DENIED;
		}

		return ACCESS_GRANTED;

	}

}
