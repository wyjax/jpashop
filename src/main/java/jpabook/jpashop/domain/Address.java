package jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

// 내장 타입 >> 어딘가에 내장될 수 있음
@Embeddable
@Getter
public class Address {

    // 값 타입은 변경  불가능하게 설계해야 한다. setter를 제거하고 getter만 남김
    private String city;
    private String street;
    private String zipcode;

    // jpa 스펙상 엔티티나 임베디드타입은 자바 기본 생성자를 public, protected로 설정해야 한다.
    // public으로 설정하는 것보다는 protected로 설정하는 것이 안전하다.
    // JPA가 이런 제약을 두는 이유는 JPA구현 라이브러리가 객체를 생성할 때 리플렉션 같은 기술을
    // 사용할 수 있도록 지원해야 하기 때문이다.
    protected Address() {
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}