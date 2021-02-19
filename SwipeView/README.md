# Topic

<pre>
<h2>Swife View 생성</h2>

1. TableLayout과 ViewPager2 사용
2. Selected Tab Icon Color and Text Color Change - 선택된 탭의 아이콘, 텍스트 컬러 변경

3. Tab Bar Top or Bottom - 탭(네비) 바 위치 위쪽 혹은 아래쪽 배치
=> 위쪽에 위치하게 하기 위해서는 TabLayout -> ViewPager2 순으로 레이아웃 구성
=> 아래쪽에 위치하게 하기 위해서는 ViewPager2 -> TabLayout 순으로 레이아웃 구성


<h2>TabLayout Attributes</h2>
= 동적 =
tabRippleColor : 탭 메뉴 클릭 중 보이는 백그라운드 Color
tabSelectedTextColor : 선택된 탭 메뉴 텍스트 Color

= 정적 =
tabTextColor : 탭 텍스트 Color
tabIconTint : 탭 아이템 아이콘 Color

= Indicator =
tabIndicatorColor : 선택된 탭 표시하는 지표[Indicator](안내 바) Color
tabIndicatorHeight : Indicator 높이(크기) 지정
tabIndicatorGravity : Indicator 위치 지정(Default : Bottom)

= 기타 =
background : Tab Layout Color

</pre>

<br><br>

# Preview

![preview](preview.gif)
<br><br>
![preview2](preview2.gif)
