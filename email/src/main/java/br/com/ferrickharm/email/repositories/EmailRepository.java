package br.com.ferrickharm.email.repositories;

import br.com.ferrickharm.email.models.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<Email, Long> {
}
