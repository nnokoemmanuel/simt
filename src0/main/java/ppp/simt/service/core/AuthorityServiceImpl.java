package ppp.simt.service.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ppp.simt.entity.core.Authority;
import ppp.simt.repository.core.AuthorityRepository;

import java.util.List;


@Service
public class AuthorityServiceImpl implements AuthorityService {

	@Autowired
	private AuthorityRepository authorityRepository;
	
	
	@Override
	public List<Authority> findAll() {
		return authorityRepository.findAll();
	}

	
	
	@Override
	public void createAuthority(Authority authority) {
		authorityRepository.save(authority);
		
	}

	@Override
	public void updateAuthority(Authority authority) {
		authorityRepository.saveAndFlush(authority);
		
	}
	
	@Override
	public Authority findAuthorityById(int authorityId) {
		
		return authorityRepository.findById(authorityId);
	}
}
