package com.likelion.likelionproject.service;

import com.likelion.likelionproject.dto.post.PostDetailResponse;
import com.likelion.likelionproject.dto.post.PostPageResponse;
import com.likelion.likelionproject.dto.post.PostRequest;
import com.likelion.likelionproject.dto.post.PostDto;
import com.likelion.likelionproject.entity.Post;
import com.likelion.likelionproject.entity.User;
import com.likelion.likelionproject.enums.ErrorCode;
import com.likelion.likelionproject.exception.AppException;
import com.likelion.likelionproject.repository.PostRepository;
import com.likelion.likelionproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    /**
     * 권한 확인
     */
    private Post checkPermission(Long id, String userName) {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND, ErrorCode.USERNAME_NOT_FOUND.getMessage()));
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));
        if (!Objects.equals(post.getUser().getId(), user.getId())) {
            throw new AppException(ErrorCode.INVALID_PERMISSION, ErrorCode.INVALID_PERMISSION.getMessage());
        }

        return post;
    }

    /**
     * 마이 피드
     */
    public Page<PostDetailResponse> my(String userName, Pageable pageable) {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND, ErrorCode.USERNAME_NOT_FOUND.getMessage()));

        Page<PostDetailResponse> postDetailResponses = postRepository.findByUserId(user.getId(), pageable).map(PostDetailResponse::fromEntity);

        return postDetailResponses;
    }

    /**
     * 포스트 리스트
     */
    public PostPageResponse list(Pageable pageable) {
        Page<PostDetailResponse> postDetailResponses = postRepository.findAll(pageable).map(PostDetailResponse::fromEntity);

        PostPageResponse postPageResponse = PostPageResponse.builder()
                .content(postDetailResponses.getContent())
                .pageable("INSTANCE")
                .last(postDetailResponses.hasNext())
                .totalPages(postDetailResponses.getTotalPages())
                .totalElements(postDetailResponses.getTotalElements())
                .size(postDetailResponses.getSize())
                .number(postDetailResponses.getNumber())
                .sort(postDetailResponses.getSort())
                .first(postDetailResponses.isFirst())
                .numberOfElements(postDetailResponses.getNumberOfElements())
                .empty(postDetailResponses.isEmpty())
                .build();

        return postPageResponse;
    }

    /**
     * 포스트 상세
     */
    public PostDetailResponse detail(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));

        return PostDetailResponse.fromEntity(post);
    }

    /**
     * 포스트 등록
     */
    public PostDto create(PostRequest request, String userName) {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND, ErrorCode.USERNAME_NOT_FOUND.getMessage()));

        Post savedPost = postRepository.save(request.toEntity(user));

        return PostDto.fromEntity(savedPost);
    }

    /**
     * 포스트 수정
     */
    public PostDto edit(Long id, PostRequest request, String userName) {
        Post post = checkPermission(id, userName);

        post.postEdit(request);
        Post editPost = postRepository.save(post);

        return PostDto.fromEntity(editPost);
    }

    /**
     * 포스트 삭제
     */
    public void delete(Long id, String userName) {
        Post post = checkPermission(id, userName);

        postRepository.delete(post);
    }
}
