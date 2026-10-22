package com.kenndevs.backend.contact.repository;

import com.kenndevs.backend.contact.entity.ContactInquiry;
import com.kenndevs.backend.contact.entity.InquiryStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ContactInquiryRepository extends JpaRepository<ContactInquiry, UUID> {

    Page<ContactInquiry> findByStatus(InquiryStatus status, Pageable pageable);

    Page<ContactInquiry> findByEmailIgnoreCase(String email, Pageable pageable);

    long countByStatus(InquiryStatus status);
}
