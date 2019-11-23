package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class) // 실행할 때 스프링이랑 같이 엮어서 실행할 때 추가
@SpringBootTest // 스프링 부트를 뛰운 상태로 테스트를 하려면 이것을 추가해야한다.
@Transactional // trasaction을 걸고 테스트를하고 테스트가 끝나면 rollback해야 한다.
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    /*
        테스트는 given, when, then을 하는게 좋다
        - 메모리 DB를 써서 테스트와 서비스를 격리한다.
     */

    @Test
    @Rollback(true) // 기본적으로 trasaction은 rollback을 하기 때문에 insert문이 안보임 보려면 이거를 써줘야함
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("eom");
        // 테스트라는게 기본적으로 이런게 좋아졌는데 이렇게 하면 이렇게 해라라는 것
        // 이런 스타일로 하는게 테스트, 유지보수하는게 좋다
        //when
        Long saveId = memberService.join(member);
        //then
        // 아래가 가능하면 회원가입이 정상적으로 된 것이다.
        assertEquals(member, memberRepository.findOne(saveId));
    }
    
    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("eom");

        Member member2 = new Member();
        member2.setName("eom");
        //when
        memberService.join(member1);
        memberService.join(member2); // 중복회원이므로 예외가 발생해야한다.
        //then
        fail("예외가 발생해야 합니다."); // assert junit
    }
}