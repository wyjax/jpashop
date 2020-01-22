package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryOld {

    // entityManager는 PersistenceContext가 있어야 인젝션이 된다. ++ @Autowired도 인젝션되게 해줌..
    // @PersistenceContext
    private final EntityManager em;
    /*
    public MemberRepository(EntityManager em) {
        this.em = em;
    }
     */

    public void save(Member member) {
        // 이렇게 하면 저장해준다.
        em.persist(member);
    }

    public Member findOne(Long id) {
        // member.class와 id를 넘기면 member를 찾아서 반환해준다.
        return em.find(Member.class, id);
    }

    // JPQL은 SQL이랑 거의 똑같은데 다름 / 차이 : entity객체를 대상으로 쿼리를 한다.
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
