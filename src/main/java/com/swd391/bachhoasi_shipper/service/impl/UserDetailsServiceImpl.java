package com.swd391.bachhoasi_shipper.service.impl;

import com.swd391.bachhoasi_shipper.model.entity.Shipper;
import com.swd391.bachhoasi_shipper.repository.ShipperRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.swd391.bachhoasi_shipper.model.entity.Admin;
import com.swd391.bachhoasi_shipper.repository.AdminRepository;

import java.util.Collection;
import java.util.Collections;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final ShipperRepository shipperRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        Shipper shipper = shipperRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Wrong username, please check again !!!"));
        return new User(shipper.getEmail(), shipper.getHashPassword(), rolesToAuthority(shipper));
    }

    private Collection<GrantedAuthority> rolesToAuthority(Shipper user) {
        return Collections.EMPTY_LIST;
    }
}
