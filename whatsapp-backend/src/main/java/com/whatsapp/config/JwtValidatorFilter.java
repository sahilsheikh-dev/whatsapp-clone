package com.whatsapp.config;

import java.util.List;
import java.io.IOException;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.GrantedAuthority;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtValidatorFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String jwt = request.getHeader(SecurityConstant.HEADER);

		System.out.println("validator jwt -------- " + jwt);

		if (jwt != null) {

			try {

				jwt = jwt.substring(7);

				SecretKey key = Keys.hmacShaKeyFor(SecurityConstant.JWT_KEY.getBytes());

				Claims claim = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();

				String username = String.valueOf(claim.get("username"));
				String authorities = String.valueOf(claim.get("authorities"));

				List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);

				Authentication auth = new UsernamePasswordAuthenticationToken(username, null, auths);

				SecurityContextHolder.getContext().setAuthentication(auth);
			} catch (Exception e) {
				System.out.println("invalid token recived...................");
				throw new BadCredentialsException("invalid token");
				// TODO: handle exception
			}

		}
		filterChain.doFilter(request, response);

	}

//	protected boolean shouldNotFilter(HttpServletRequest request) {
//		return request.getServletPath().equals("/sigin");
//	}

}
