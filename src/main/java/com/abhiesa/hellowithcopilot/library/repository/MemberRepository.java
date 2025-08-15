package com.abhiesa.hellowithcopilot.library.repository;

import com.abhiesa.hellowithcopilot.library.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for Member entity.
 * Provides CRUD operations for members.
 * Defensive programming: always validate input parameters in custom methods.
 */
public interface MemberRepository extends JpaRepository<Member, Long> {

}
