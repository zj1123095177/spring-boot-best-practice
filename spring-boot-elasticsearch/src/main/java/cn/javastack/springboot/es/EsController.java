package cn.javastack.springboot.es;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 微信公众号：Java技术栈
 */
@RequiredArgsConstructor
@RestController
public class EsController {

    public static final String INDEX_JAVASTACK = "javastack";
    private final ElasticsearchTemplate elasticsearchTemplate;

    private final UserRepository userRepository;

    @GetMapping("/es/insert")
    public User insert(@RequestParam("name") String name, @RequestParam("sex") int sex)
    {
        IndexCoordinates indexCoordinates =  IndexCoordinates.of(INDEX_JAVASTACK);
        // 新增
        User user = new User(RandomUtils.nextInt(), name, sex);
        return elasticsearchTemplate.save(user, indexCoordinates);
    }

    @GetMapping("/es/get")
    public User esGet(@RequestParam("name") String name) {
        IndexCoordinates indexCoordinates =  IndexCoordinates.of(INDEX_JAVASTACK);
        Query query = new CriteriaQuery(Criteria.where("name").is(name));
        SearchHit<User> userSearchHit = elasticsearchTemplate.searchOne(query, User.class, indexCoordinates);
        if (userSearchHit != null) {
            return userSearchHit.getContent();
        }
        return null;
    }

    @RequestMapping("/es/repo/insert")
    public User repoInsert(@RequestParam("name") String name, @RequestParam("sex") int sex) {
        // 新增
        User user = new User(RandomUtils.nextInt(), name, sex);
        userRepository.save(user);

        // 查询
        return userRepository.findByName(name).get(0);
    }

}
