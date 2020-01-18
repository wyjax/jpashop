package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id", unique = true)
    private Long id;

    @NotEmpty
    private String name;

    @Embedded // embeddable & embedded 중에 하나만 있으면됨
    private Address address;

    @JsonIgnore // 그냥 넣으면 엔티티가 노출된다. 이거를 넣으면 주먼종보가 빠진다.
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();
}