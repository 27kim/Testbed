package io.lab27.githubuser.data.datasource.remote

import io.lab27.githubuser.data.datasource.remote.MenuModel.Companion.TITLE
import io.lab27.githubuser.data.datasource.remote.MenuModel.Companion.TYPE_MENU_1
import io.lab27.githubuser.data.datasource.remote.MenuModel.Companion.TYPE_MENU_2
import io.lab27.githubuser.data.datasource.remote.MenuModel.Companion.TYPE_MENU_3
import io.lab27.githubuser.data.datasource.remote.MenuModel.Companion.TYPE_MENU_4
import io.lab27.githubuser.network.api.MHApi

interface MoreRepository {
    fun fetchMainMenu(): List<MenuModel>
    fun fetchSettingMenu(): List<MenuModel>
    fun fetchAppList(): List<MenuItem>
    fun fetchUsageMenu(): List<String>
}

class MoreRepositoryImpl(moreApi: MHApi) : MoreRepository {
    override fun fetchMainMenu(): List<MenuModel> = listOf(
        MenuModel(TITLE, "설정"),
        MenuModel(TYPE_MENU_1, "알림 설정"),
        MenuModel(TYPE_MENU_1, "차량 공유 및 등록 관리"),
        MenuModel(TYPE_MENU_1, "주유비 자동 입력 설정"),
        MenuModel(TYPE_MENU_1, "블루 멤버스 비밀번호 변경"),
        MenuModel(TYPE_MENU_1, "결제 카드 관리"),
        MenuModel(TITLE, "이용 내역"),
        MenuModel(TYPE_MENU_1, "카라이프 서비스 이용 내역"),
        MenuModel(TYPE_MENU_1, "블루멤버스 포인트 몰 구매 내역")
    )

    override fun fetchSettingMenu(): List<MenuModel> = listOf(
        MenuModel(TITLE, "로그인 기본 설정"),
        MenuModel(TYPE_MENU_2, "김현대"),
        MenuModel(TYPE_MENU_1, "회원 정보 수정"),
        MenuModel(TYPE_MENU_1, "서비스 탈퇴"),
        MenuModel(TITLE, "앱 위치 정보 서비스"),
        MenuModel(TYPE_MENU_3, "위치 정보 서비스 이용 동의"),
        MenuModel(TITLE, "앱 정보"),
        MenuModel(TYPE_MENU_4, "현재 버전"),
        MenuModel(TYPE_MENU_4, "최신 버전"),
        MenuModel(TITLE, "회원 약관 및 정책"),
        MenuModel(TYPE_MENU_1, "이용 약관"),
        MenuModel(TYPE_MENU_1, "개인정보 처리 방침"),
        MenuModel(TYPE_MENU_1, "위치 기반 서비스 이용 약관"),
        MenuModel(TYPE_MENU_1, "정보 제공처"),
        MenuModel(TYPE_MENU_1, "오픈소스 라이선스")
    )

    override fun fetchAppList(): List<MenuItem> = listOf(
        MenuItem("블루링크", android.R.drawable.ic_menu_camera),
        MenuItem("디지털키", android.R.drawable.ic_menu_add),
        MenuItem("셀렉션", android.R.drawable.ic_menu_zoom),
        MenuItem("카페이", android.R.drawable.ic_menu_save)
    )

    override fun fetchUsageMenu(): List<String> = listOf(
        "카라이프 서비스 이용 내역",
        "블루멤버스 포인트 몰 구매 내역"
    )
}

data class MenuModel(val type: Int, val text: String) {
    companion object {
        const val TITLE = 0
        const val TYPE_MENU_0 = 0 //메인 메뉴 - 텍스트 only
        const val TYPE_MENU_1 = 1 //메인 메뉴 - >
        const val TYPE_MENU_2 = 2 //메인 메뉴 - 버튼
        const val TYPE_MENU_3 = 3 //메인 메뉴 - 스위치
        const val TYPE_MENU_4 = 4 //메인 메뉴 - 좌측 텍스트 포함
        const val TYPE_MENU_5 = 5 //메인 메뉴
        const val TYPE_MENU_6 = 6 //메인 메뉴
        const val TYPE_MENU_7 = 7 //메인 메뉴
        const val TYPE_MENU_8 = 8 //메인 메뉴
        const val TYPE_MENU_9 = 9 //메인 메뉴
    }
}

data class MenuItem(val title: String, val icon: Int)
