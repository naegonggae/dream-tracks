package com.final_project_leesanghun_team2.service;

import com.final_project_leesanghun_team2.exception.tag.NoSuchTagException;
import com.final_project_leesanghun_team2.domain.dto.tag.TagSaveRequest;
import com.final_project_leesanghun_team2.domain.dto.tag.TagSaveResponse;
import com.final_project_leesanghun_team2.domain.entity.Post;
import com.final_project_leesanghun_team2.domain.entity.Tag;
import com.final_project_leesanghun_team2.exception.post.NoSuchPostException;
import com.final_project_leesanghun_team2.repository.PostRepository;
import com.final_project_leesanghun_team2.repository.TagPostRepository;
import com.final_project_leesanghun_team2.repository.TagRepository;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class TagService {

	private final TagRepository tagRepository;
	private final TagPostRepository tagPostRepository;
	private final PostRepository postRepository;

	// 태그 생성
	@Transactional
	public TagSaveResponse save(TagSaveRequest request) {

//		boolean isExist = tagRepository.existsByName(request.getName());
//		if (isExist) {
//			Tag tag = tagRepository.findByName(request.getName())
//					.orElseThrow(NoSuchTagException::new);
//			return TagSaveResponse.from(tag);
//		}
//		else {
//			Tag tag = Tag.createTag(request);
//			Tag savedTag = tagRepository.save(tag);
//			return TagSaveResponse.from(savedTag);
//		}
		log.info("tag 저장 시작");
		log.info(request.getName());
		System.out.println("request = " + request.getName());
		Tag findTag = tagRepository.findByName(request.getName())
				.orElseGet(() -> {
					log.info("조회했더니 없어서 생성하러 들어옴");
					Tag tag = Tag.createTag(request);
					return tagRepository.save(tag);
				});
//		Tag tag = Tag.createTag(request);
//		Tag savedTag = tagRepository.save(tag);

		log.info(findTag.getName());
		log.info("tag 저장 종료");

		return TagSaveResponse.from(findTag);
//		return tagRepository.findByName(request.getName())
//				.map(TagSaveResponse::from)
//				.orElseGet(() -> {
//					Tag tag = Tag.createTag(request);
//					Tag savedTag = tagRepository.save(tag);
//					return TagSaveResponse.from(savedTag);
//				});
	}

	// tagPost 삭제
	@Transactional
	public void delete(String tagName, Long postId) {

		Tag tag = tagRepository.findByName(tagName)
				.orElseThrow(NoSuchTagException::new);

		Post post = postRepository.findById(postId)
				.orElseThrow(NoSuchPostException::new);

		// 연관관계 유지를 해줘야할 것 같음
		tagPostRepository.deleteByTagAndPost(tag, post);
	}

}
