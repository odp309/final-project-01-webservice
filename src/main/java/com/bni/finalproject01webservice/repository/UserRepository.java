package com.bni.finalproject01webservice.repository;

import com.bni.finalproject01webservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    User findByEmail(String email);

    @Modifying
    @Transactional
    @Query(value = """
            update
            	users
            set
            	is_cooldown = false,
            	updated_at = current_timestamp
            where id in (
            	select
            		u.id
            	from
            		users u
            	join withdrawal w on
            		u.id = w.user_id
            	where
            		w.status = 'Kadaluarsa'
            		and u.is_cooldown = true
            		and DATE(w.reservation_date) <= CURRENT_DATE - interval '3' day
            		and w.created_at = (
            		select
            			MAX(w2.created_at)
            		from
            			withdrawal w2
            		where
            			w2.user_id = u.id
            			and w2.status = 'Kadaluarsa'
            	        )
            	group by
            		u.id
            )
            """, nativeQuery = true)
    void findUsersWithExpiredWithdrawalsAndCooldown();

    @Query("""
            select
                u
            from
                User u
            where
                u.id IN :ids
            """)
    List<User> findUsersByIds(@Param("ids") List<UUID> ids);

    @Modifying
    @Transactional
    @Query("""
            update
                User u
            set
                u.isCooldown = true,
                u.updatedAt = current_timestamp
            where
                u.id = :id
            """)
    void updateIsCooldownToTrue(@Param("id") UUID id);

    @Modifying
    @Transactional
    @Query("""
            update
                User u
            set
                u.isCooldown = false,
                u.updatedAt = current_timestamp
            where
                u.id = :id
            """)
    void updateIsCooldownToFalse(@Param("id") UUID id);
}
