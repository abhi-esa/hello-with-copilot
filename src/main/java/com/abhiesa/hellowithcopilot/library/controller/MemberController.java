package com.abhiesa.hellowithcopilot.library.controller;

import com.abhiesa.hellowithcopilot.library.model.Member;
import com.abhiesa.hellowithcopilot.library.service.MemberService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing library members. Applies defensive programming, bean validation, and
 * comprehensive documentation.
 */
@RestController
@RequestMapping("/api/members")
public class MemberController {

  private final MemberService members;

  /**
   * Constructs a MemberController with the given MemberService.
   *
   * @param members the service to manage members
   * @throws IllegalArgumentException if members is null
   */
  public MemberController(MemberService members) {
    if (members == null) {
      throw new IllegalArgumentException("MemberService cannot be null");
    }
    this.members = members;
  }

  /**
   * Creates a new member.
   *
   * @param m the member to create
   * @return ResponseEntity containing the created member or bad request if input is invalid
   */
  @PostMapping
  public ResponseEntity<Member> create(@Valid @RequestBody Member m) {
    if (m == null) {
      return ResponseEntity.badRequest().build();
    }
    Member created = members.create(m);
    if (created == null) {
      return ResponseEntity.badRequest().build();
    }
    return ResponseEntity.ok(created);
  }

  /**
   * Retrieves a member by their ID.
   *
   * @param id the ID of the member
   * @return ResponseEntity containing the found member, not found, or bad request if ID is invalid
   */
  @GetMapping("/{id}")
  public ResponseEntity<Member> get(@PathVariable Long id) {
    if (id == null || id <= 0) {
      return ResponseEntity.badRequest().build();
    }
    Member found = members.get(id);
    if (found == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(found);
  }

  /**
   * Lists all members.
   *
   * @return ResponseEntity containing the list of members
   */
  @GetMapping
  public ResponseEntity<List<Member>> list() {
    List<Member> memberList = members.list();
    return ResponseEntity.ok(memberList == null ? List.of() : memberList);
  }

  /**
   * Updates an existing member by their ID.
   *
   * @param id the ID of the member to update
   * @param m  the updated member data
   * @return ResponseEntity containing the updated member, not found, or bad request if input is
   * invalid
   */
  @PutMapping("/{id}")
  public ResponseEntity<Member> update(@PathVariable Long id, @Valid @RequestBody Member m) {
    if (id == null || id <= 0 || m == null) {
      return ResponseEntity.badRequest().build();
    }
    m.setId(id);
    Member updated = members.update(m);
    if (updated == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(updated);
  }

  /**
   * Deletes a member by their ID.
   *
   * @param id the ID of the member to delete
   * @return ResponseEntity with no content, or bad request if ID is invalid
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    if (id == null || id <= 0) {
      return ResponseEntity.badRequest().build();
    }
    members.delete(id);
    return ResponseEntity.noContent().build();
  }
}