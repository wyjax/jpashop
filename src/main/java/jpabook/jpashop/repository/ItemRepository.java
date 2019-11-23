package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    // data jpa가 이것을 해준다.
    private final EntityManager em;

    // 상품저장
    public void save(Item item) {
        // id 값이 없다는 것은 완전히 새로 생성한 객체라는 것이다.
        // id 값이 있으면 이미 db에서 가져온 것이다. >> save는 update랑 비슷하다고 보면 된다.
        if (item.getId() == null) {
            em.persist(item);
        }
        else {
            em.merge(item);
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
