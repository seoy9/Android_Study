# Chapter 06 화면 구성하기_레이아웃(뷰 그룹)

## 6.0 들어가며

### 6.0.1 레이아웃 소개

- 레이아웃 : 뷰를 배치하는 구성요소
- 뷰 : 버튼, 텍스트뷰, 이미지뷰 등과 같이 눈에 보이는 모든 화면 요소들을 뜻함
- 뷰들이 여기저기 산재해 있으면 사용자가 화면을 효율적으로 사용하지 못하게 됨  
⇒ 그래서 레이아웃이 필요함
- 레이아웃 종류 : 리니어 레이아웃, 상대적 레이아웃, 컨스트레인트 레이아웃, 테이블 레이아웃, 프레임 레이아웃 등
- 가장 많이 사용되는 레이아웃 : 리니어/상대적/컨스트레인트 레이아웃
</br></br></br>

## 6.1 레이아웃 종류

- 리니어 레이아웃 (LinearLayout)
    - 수직 방향 (위에서 아래로) 혹은 수평 방향 (왼쪽에서 오른쪽으로) 차례로 주어진 뷰를 정렬
- 상대적 레이아웃 (RelativeLayout)
    - 상대적 레이아웃을 사용하면 뷰들이 다른 뷰들로부터 위치를 지정하거나 자신이 속한 레이아웃을 기준으로 위치를 정함
    - 예) ‘A 뷰의 오른쪽에 위치’, ‘부모 레이아웃의 정중앙에 위치’와 같이 지정 가능
- 컨스트레인트 레이아웃 (ConstraintLayout)
    - 뷰 사이에 수평, 수직 방향의 제약을 주어 뷰들을 위치시킴
- 테이블 레이아웃 (TableLayout)
    - 뷰를 행과 열로 구성하여 표(테이블)의 형태로 표현
- 프레임 레이아웃 (FrameLayout)
    - 뷰들을 액자처럼 쌓아놓음
    - 여러 뷰들을 추가하더라도 가장 나중에 추가한 뷰가 가장 위에 위치하게 됨
    - 레이아웃 내에 여러 뷰들을 배치시키는 데는 적합하지 않고 주로 화면에 표시될 하나의 뷰를 바꿔가며 표시하는 데 적합
</br></br></br>

## 6.2 리니어 레이아웃 : LinearLayout

- 리니어 (Linear) : ‘직선 모양의’이라는 뜻
- 이 레이아웃을 사용하면 뷰들이 새로 또는 가로 방향 직성 모양으로 정렬됨
- 뷰가 쌓이는 순서가 중요함
</br></br>

### 6.2.1 리니어 레이아웃의 기본 속성

- 리니어 레이아웃은 다른 레이아웃과 다르게 방향 속성을 꼭 지정해주어야 함  
→ 그렇지 않으면 뷰들을 세로로 구성할지, 가로로 구성할지 알 방법이 없음
- 방향을 설정할 때는, 세로 방향은 vertical (수직), 가로 방향은 horizontal (수평)로 orientation (방향) 속성을 지정해주면 됨
- 리니어 레이아웃의 orientation 속성을 이용해 그 안에 있는 뷰 정렬 방식을 지정할 수 있음
</br></br>

### 6.2.2 독립적으로 위치를 지정하는 layout_gravity 속성 알아보기

- orientation 은 레이아웃 속성 자체에 값을 주어 종속된 모든 뷰가 영향을 받음
- 리니어 레이아웃 안에 있는 뷰에 layout_gravity 속성을 주어 리니어 레이아웃 안에서 독립적으로 자신의 위치를 정할 수 있음

| vertical | 수직 | horizontal | 수평 |
| --- | --- | --- | --- |
| start | 맨 앞 | top | 맨 위 |
| center | 가운데 | center | 가운데 |
| end | 맨 뒤 | bottom | 맨 아래 |
</br>

### 6.2.3 비중을 지정하는 layout_weight 속성 알아보기

- 버튼 3개를 1:2:1 비율로 부모 레이아웃의 가로에 꽉차게 배치하고 싶으면 → layout_weight 사용
- 각 크기에 가중치 (weight) 를 둔다고 생각하면 됨
</br></br></br>

## 6.3 상대적 레이아웃 : RelativeLayout

- 상대적 레이아웃 : 다른 뷰를 기준으로 상대적 위치를 지정하는 레이아웃
- 리니어 레이아웃을 사용할 때보다 조금 더 복잡한 레이아웃 구성을 목표로 할 때 적합
</br></br>

### 6.3.1 상대적 레이아웃의 기본 속성

- 상대적 레이아웃은 내부에 뷰를 배치할 때 기준이 되는 뷰가 존재해야 함
- 다른 뷰가 될 수도 있고. 부모 레이아웃 역시 기준이 될 수 있음
- 부모 레이아웃 기준으로 한 상대적 레이아웃 속성
    
    
    | 속성값 | 위치 |
    | --- | --- |
    | android:layout_alignParentStart | 부모 레이아웃에서 시작점 |
    | android:layout_alignParentEnd | 부모 레이아웃에서 끝점 |
    | android:layout_alignParentBottom | 부모 레이아웃에서 아래쪽 |
    | android:layout_centerInParent | 부모 레이아웃의 정중앙 |
    | android:layout_centerHorizontal | 부모 레이아웃에 수평 방향으로 중앙 |
    | android:layout_centerVertical | 부모 레이아웃에 수직 방향으로 중앙 |
    | android:layout_alignParentLeft | 부모 레이아웃의 왼쪽 |
    | android:layout_alignParentRight | 부모 레이아웃의 오른쪽 |
    | android:layout_alignParentTop | 부모 레이아웃의 위쪽 |
    - 자식 뷰들은 서로 간의 위치에 영향을 주지 않고 오로지 부모 레이아웃을 기준으로 배치됨
- 자식 뷰 기준으로 한 상대적 레이아웃 속성
    
    
    | 속성값 | 위치 |
    | --- | --- |
    | android:layout_toLeftOf | 기준이 되는 뷰의 왼쪽에 위치 |
    | android:layout_above | 기준이 되는 뷰의 위에 위치 |
    | android:layout_toRightOf | 기준이 되는 뷰의 오른쪽에 위치 |
    | android:layout_below | 기준이 되는 뷰의 아래에 위치 |
    | android:layout_toStartOf | 기준이 되는 뷰의 시작점에 대상 뷰의 끝점을 위치 |
    | android:layout_toEndOf | 기준이 되는 뷰의 끝에 대상 뷰의 시작점을 위치 |
</br></br>

## 6.4 컨스트레인트 레이아웃 : ConstraintLayout

- 컨스트레인트 (Constraint) : “제약”
- 한 화면을 구성하는 뷰들에 서로 제약을 줌
- 리니어 레이아웃이나 상대적 레이아웃보다 더 자주 쓰는 가장 큰 이유
→ 다양한 화면 크기에 대응하는 반응형 UI를 쉽게 구성할 수 있기 때문
→ 중첩된 레이아웃을 사용하지 않고도 크고 복잡한 레이아웃을 만들 수 있어 성능면에서 유리
- 리니어 레이아웃과 상대적 레이아웃은 잘 모르더라도 컨스트레인트 레이아웃은 꼭 잘 알아야 함
</br></br>

### 6.4.1 컨스트레인트 레이아웃 기본 속성

- 컨스트레인트 레이아웃에서 자식 뷰의 위치를 정의하려면 자식 뷰의 수직/수평 방향에 제약 조건을 각각 하나 이상 추가해야 함
- 자식 뷰에 아무런 제약도 추가해주지 않으면 왼쪽 상단에 배치됨
- 컨스크레인트 레이아웃에서 자주 쓰는 속성
    
    ```kotlin
    app:layout_constraint[내 방향]_to[기준 뷰 방향]Of = "[기준 뷰 ID or parent]"
    ```
    
</br>

### 6.4.2 컨스트레인트 레이아웃에서 마진을 줄 때 주의점

- 자식 뷰 사이에 여백을 정해줄 때 레이아웃에서는 layout_margin 속성을 사용
- 리니어 레이아웃과 상대적 레이아웃에서는 별다른 고려 없이 사용
But, 컨스트레인트 레이아웃은 반드시 해당 방향으로 제약이 존재해야 마진값이 적용된다는 규칙 있음
</br></br>

### 6.4.3 match_constraint 속성

- 컨스트레인트 레이아웃을 이용해 앱의 레이아웃을 구성하다 보면 layout_width와 layout_height 속성에서 빈번하게 0dp 값을 보게 됨  
⇒ 0dp 는 match_constraint를 값으로 주는 것과 같음
- match_parent : 부모 레이아웃 크기에 뷰 크기를 맞추는 것
- match_constraint : 제약에 뷰 크기를 맞추는 것
</br></br></br>

## 6.5 반응형 UI 만들기 : Guideline

- 가이드 라인 : 실제 화면에는 보이지 않으며, 레이아웃을 구성할 때만 사용되는 도구  
→ 어떤 기기 해상도에서도 일정한 비율로 레이아웃을 구성하고 싶을 때 굉장히 유용하게 사용됨
</br></br></br>
## 핵심 요약

- 레이아웃은 뷰를 배치하는 구성요소
- 리니어 레이아웃은 방향성을 가지고 뷰들을 차례로 배치시킴
- 상대적 레이아웃은 뷰가 다른 뷰나 레이아웃을 기준으로 삼고 배치되게 함
- 컨스트레인트 레이아웃은 뷰가 수직 및 수평 방향에 제약 조건을 각각 하나 이상 추가하여 배치되게 함