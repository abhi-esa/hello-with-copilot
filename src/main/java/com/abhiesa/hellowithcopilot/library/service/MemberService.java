package com.abhiesa.hellowithcopilot.library.service;

import com.abhiesa.hellowithcopilot.library.model.Member;
import com.abhiesa.hellowithcopilot.library.repository.MemberRepository;
import com.abhiesa.hellowithcopilot.library.exception.NotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;
import jakarta.validation.Valid;

/**
 * Service class for managing members.
 * Applies defensive programming, bean validation, and provides comprehensive documentation.
 */
@Service
public class MemberService {
  private final MemberRepository members;

  /**
   * Constructs a MemberService with the given MemberRepository.
   * @param members MemberRepository instance
   * @throws IllegalArgumentException if members is null
   */
  public MemberService(MemberRepository members) {
    if (members == null) {
      throw new IllegalArgumentException("MemberRepository cannot be null");
    }
    this.members = members;
  }

  /**
   * Creates a new member.
   * @param m Member entity
   * @return persisted Member
   * @throws IllegalArgumentException if member is null
   */
  public Member create(@Valid Member m) {
    if (m == null) {
      throw new IllegalArgumentException("Member cannot be null");
    }
    return members.save(m);
  }

  /**
   * Retrieves a member by its ID.
   * @param id Member ID
   * @return Member entity
   * @throws IllegalArgumentException if id is null or not positive
   * @throws NotFoundException if member not found
   */
  public Member get(Long id) {
    if (id == null || id <= 0) {
      throw new IllegalArgumentException("Member ID must be positive");
    }
    return members.findById(id).orElseThrow(() -> new NotFoundException("Member not found: " + id));
  }

  /**
   * Lists all members.
   * @return List of members
   */
  public List<Member> list() {
    return members.findAll();
  }

  /**
   * Updates an existing member.
   * @param m Member entity
   * @return updated Member
   * @throws IllegalArgumentException if member is null or id is invalid
   */
  public Member update(@Valid Member m) {
    if (m == null || m.getId() == null || m.getId() <= 0) {
      throw new IllegalArgumentException("Member or Member ID is invalid");
    }
    get(m.getId());
    return members.save(m);
  }

  /**
   * Deletes a member by its ID.
   * @param id Member ID
   * @throws IllegalArgumentException if id is invalid
   */
  public void delete(Long id) {
    if (id == null || id <= 0) {
      throw new IllegalArgumentException("Member ID must be positive");
    }
    members.deleteById(id);
  }
}