package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    // 수정 >> 변경감지
    @Transactional
    public void updateItem(Long id, String name, int price, int stockQuantity) {
        // 트랜잭션안에서 조회를 해야 영속성 상태로 조회가 된다.
        Item item = itemRepository.findOne(id);
        item.change(name, price, stockQuantity);
//        item.setName(name);
//        item.setPrice(price);
//        item.setStockQuantity(stockQuantity);
        // TX(transaction)은 끝날 때 commit을 한다 > 그래서 item이 update가 된다.
    }

    // items find
    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    // item find
    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }
}