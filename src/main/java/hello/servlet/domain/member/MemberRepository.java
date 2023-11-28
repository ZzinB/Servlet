package hello.servlet.domain.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 동시성 문제가 고려되어 있지 않음, 실무에서는 ConcurrentHashMap, AtomicLong 사용 고려
 */
public class MemberRepository {
    //싱글톤이기 때문에 아래의 store, sequence는 static으로 하지 않아도 된다.
    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    private static final MemberRepository instance = new MemberRepository();

    public static MemberRepository getInstance(){
        return instance;
    }

    private MemberRepository(){
    }

    //저장
    public Member save(Member member){
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    //id 검색
    public Member findById(Long id){
        return store.get(id);
    }

    //전체 출력
    public List<Member> findAll(){
        //store에 있는 모든 값들을 다 꺼내서 새로운 ArrayList에 담아서 넘겨준다.
        // -> ArrayList에 있는 값들을 조작해도 store에 있는 value를 건들이지 않게하기 위해(store 자체 보호)
        return new ArrayList<>(store.values());
    }

    //(테스트에서 사용) store 다 날리기
    public void clearStore(){
        store.clear();
    }
}
