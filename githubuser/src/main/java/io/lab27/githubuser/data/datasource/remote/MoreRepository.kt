package io.lab27.githubuser.data.datasource.remote

import io.lab27.githubuser.network.api.MHApi
import io.lab27.githubuser.network.api.UserApi

interface MoreRepository {
    fun fetchSettingMenu(): List<String>
    fun fetchUsageMenu(): List<String>
}

class MoreRepositoryImpl(moreApi: MHApi) : MoreRepository {
    override fun fetchSettingMenu() : List<String> = listOf(
        "알림 설정",
        "차량 공유 및 등록 관리",
        "주유비 자동 입력 설정",
        "블루 멤버스 비밀번호 변경",
        "결제 카드 관리"
    )

    override fun fetchUsageMenu() : List<String> = listOf(
        "카라이프 서비스 이용 내역",
        "블루멤버스 포인트 몰 구매 내역"
    )
}