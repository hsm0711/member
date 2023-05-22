package com.member.api.member;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.member.api.member.model.SignInRequest;
import com.member.api.member.model.SignInResponse;
import com.member.api.member.model.MemberInfoResponse;
import com.member.api.member.model.SignUpRequest;
import com.member.api.member.model.SignUpResponse;
import com.member.api.member.service.MemberService;
import com.member.model.BaseResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@Api(tags = "회원 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

	private final MemberService memberService;

	/*
	 * 회원가입
	 */
	@ApiOperation(value = "회원가입", notes = "회원 가입하기")
	@PostMapping("/public/signup")
	public ResponseEntity<BaseResponse<SignUpResponse>> createMember(@RequestBody SignUpRequest req) {
		return ResponseEntity.ok(new BaseResponse<>(memberService.createMember(req)));
	}

	/*
	 * 로그인
	 */
	@ApiOperation(value = "로그인", notes = "로그인 처리")
	@PostMapping("/public/login")
	public ResponseEntity<BaseResponse<SignInResponse>> login(@RequestBody SignInRequest req) {
		return ResponseEntity.ok(new BaseResponse<>(memberService.login(req)));
	}

	/*
	 * 내 정보 조회
	 */
	@ApiOperation(value = "내 정보 조회", notes = "본인의 정보를 조회")
	@GetMapping("/api/me")
	public ResponseEntity<BaseResponse<MemberInfoResponse>> myInfo(@RequestHeader(value = "Authorization") String token) {
		return ResponseEntity.ok(new BaseResponse<>(memberService.myInfo(token)));
	}

}
