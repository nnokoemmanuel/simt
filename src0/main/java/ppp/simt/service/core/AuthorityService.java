package ppp.simt.service.core;

import java.util.List;

import ppp.simt.entity.core.Authority;


public interface AuthorityService {
    public List<Authority> findAll();
	public void createAuthority(Authority authority);
	public void updateAuthority(Authority category);
	public Authority findAuthorityById(int authorityId);

}
