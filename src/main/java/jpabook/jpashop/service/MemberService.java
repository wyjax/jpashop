package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// jpa 로직같은 건 트랜잭션 안에서 실행되야 한다.
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    // 컴파일 시점에 생성자있는지 check할 수 있기 때문에 final하는 것을 추천
    private final MemberRepository memberRepository;

    /* @RequiredArgsConstructor을 안쓰면 아래 내용을 써줘야 한다.
    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    */

    /*
    @Autowired
    public void setMemberRepository(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    */
    // 단점은 애플리케이션이 돌아가는 중간에 바꾸게
    /**
     * 회원가입
     */
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    // 중복회원 검증
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        /*
            문제가 될 수 있는데 was가 동시에 뜨고 사람들이 동시에 insert를 하게 되면 동시에 호출할 수 있음
            실무에서는 최후의 방어를 해줘야 한다.
            db 멤버의 네임을 unique 제약조건을 잡아주는 것을 권장
         */
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 회원 전체 조회 > 읽기용이면 가급적이면 transactional true를 해주면 좋다.
    // @Transactional(readOnly = true) // 성능이 좋아진다. (읽기 전용)
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    // 회원 한 명 조회
    public Member findMember(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    // class에서 transactional을 readOnly로 했기 때문에 다시 써준다
    @Transactional
    public void update(Long id, String name) {
        // member는 영속상태이다
        Member member = memberRepository.findOne(id);
        member.setName(name);

        // 트랜잭션이 끝나는 시점에 jpa가 flush하고 commit한다.
    }
}
