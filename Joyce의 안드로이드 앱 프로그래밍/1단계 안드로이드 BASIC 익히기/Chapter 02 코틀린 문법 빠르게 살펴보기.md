# Chapter 02 코틀린 문법 빠르게 살펴보기

## 2.1 코틀린 실행하기

- 안드로이드 스튜디오는 코틀린 언어를 실행할 수 있는 스크래치라는 간편하고 강력한 툴을 제공
</br></br>

### 2.1.1 스크래치 실행하기

- [File] > [New] > [Scratch File] > [Kotlin]
- [Android] > [Scratches and Consoles]
- .kts 확장자인 이유 : 스크립트 파일이기 때문
- 스크립트 파일은 일반 .kt 파일과 다르게 컴파일이 없이 바로 실행되어 문법을 익히기 편함
</br></br></br></br>

## 2.2 코틀린 기본

- 변수와 상수를 선언하는 방법
- 함수의 모양새
- 코틀린에서 새롭게 소개된 Nullable (null 안정성) 개념
</br></br>

### 2.2.1 변수와 상수

- 변수 (variable) : var
- 상수 : val

```kotlin
val pi : Double = 3.14   // val 변수명 : 자료형 = 값
```

- 코틀린은 변수명을 먼저 쓰고, 콜론 : 을 쓴 후 자료형을 명시
- 문맥상 추론이 가능하면 자료형을 생략할 수 있음 ⇒ 형추론  

```kotlin
val name = "gil-dong"   // 형추론 (String)
```

- val 은 상수이므로 값을 재할당하면 컴파일 오류

```kotlin
val pi = 3.14
pi = 3.141592   // 오류 (Val cannot be reassigned)
```

- 값을 변경하고 싶을 때는 var 사용
var 로 정의된 변수의 값을 바꿀 수 있음

```kotlin
var age = 21   // 형추론 (Int)
println(age)   // 21
age = 25       // 재할당
println(age)   // 25
```
</br></br>

### 2.2.2 기본 자료형

- 자료형
    - 기본 자료형 (primitive data type)
    - 참조 자료형 (reference data type)
- 기본형 : 순수하게 값을 저장하는 자료형  
ex) 자바에서 int, byte, boolean 등
- 참조형 : 객체를 만들고 변수에는 객체의 참조값을 저장  
ex) 자바에서 String, Array 등
- 코틀린의 자료형은 모두 참조형  
⇒ 모든 자료형이 객체 형태라고 생각하면 됨
</br></br>

**숫자(정수, 실수) 자료형**

- 정수 자료형

| 자료형 | 크기 | 값의 범위 |
| --- | --- | --- |
| Byte | 1 Byte | -2^7 ~ -2^7-1 (-128 ~ 127) |
| Short | 2 Byte | -2^15 ~ 2^15-1 (-32768 ~ 32767) |
| Int | 4 Byte | -2^31 ~ 2^31-1 |
| Long | 8 Byte | -2^63 ~ 2^63-1 |
- 정수의 경우 Int형으로 추론하기 때문에, Byte나 Short 같은 작은 범위를 사용할 때는 자료형 지정

```kotlin
val numByte : Byte = 100   // val numByte: Byte
val numShort : Short = 20  // val numShort: Short
val numInt : Int = 1       // val numInt: Int
val numLong : Long = 2L    // val numLong: Long
```
</br>

- 실수 자료형

| 자료형 | 크기 | 값의 범위 |
| --- | --- | --- |
| Double | 8 Byte | 1.79E-308 ~ 1.79E+308 |
| Float | 4 Byte | 3.4E-38 ~ 3.4E+38 |
- 실수의 경우 자료형을 명시하지 않으면 Double 형이 됨
Float 형으로 지정하고 싶다면 값 뒤에 f 를 추가로 붙임

```kotlin
val numDouble : Double = 3.2   // val numDouble: Double
val numFloat : Float = 3.2f    // val numFloat : Float
```
</br>

**문자 자료형**

- 문자 하나를 표현하는 Char 형 : 문자를 작은 따음표로 감싸 표현

```kotlin
val char1 : Char = 'H'   // 자료형 생략 가능
```

- 문자열을 표현하는 String 형 : 문자열을 큰따음표로 감싸 표현

```kotlin
val string1 : String = "Hi, This is String"   // 자료형 생략 가능
```
</br>

**논리 자료형**

- 참, 거짓을 포현하는 논리 자료형, Boolean
- 값은 true, false 될 수 있음
- 주로 조건 검사에 사용

```kotlin
val isTrue : Boolean = true   // 자료형 생략 가능
```
</br>

**배열 자료형**

- 배열을 나타내는 자료형, Array
- 배열을 만드는 방법 : arrayOf( ) 라는 함수 사용
- 배열을 만들고 나서는 연산자 [ ] 를 이용해 배열의 요소에 접근 가능

```kotlin
val stringArray : Array<String> = arrayOf("apple", "banana", "grape")
val intArray = arrayOf(1, 2, 3)   // 자료형 생략

println(StringArray[0])   // apple
println(intArray[2])      // 3
```
</br>

**명시적 형변환**

- 변환될 자료형을 직접 지정하는 것 = 명시적 형변환
- Int 형을 String 형으로 만들거나, Int 형을 Double 형으로 만들거나 하는 행위

```kotlin
val score = 100   // Int 형
val scoreString = score.toString()   // String 형
val scoreDouble = score.toDouble()   // Double 형

println(scoreDouble)   // 100.0
```
</br>

### 2.2.3 함수

- 코틀린에서 함수의 기본적인 모양(문법)

```kotlin
fun 함수명 (매개변수) : 반환 자료형 {
	// 실행할 코드 내용
}
```

- 매개변수를 선언할 때, 매개변수명 : 자료형 형태로 적음
- 반환값이 필요 없으면, Unit 형
- Unit 형 : 자바의 void에 대응, 생략 가능

```kotlin
fun printAge(age : Int) : Unit {
	println(age)
}

printAge(15)   // 15

fun printAge(age : Int) {   // Unit 생략
	println(age)
}
```

- 함수의 반환값이 Unit 가 아니면, 반드시 반환형을 명시해야 함

```kotlin
fun addNum(a : Int, b : Int) : Int {
	return a + b
}

println(addNum(200, 400))   // 600
```

- 반환 자료형을 생략할 수 있는 경우
    - Unit 일 때
    - 단일 표현식 함수 : 실행할 코드가 표현식 하나로 이루어진 함수 ⇒ 형추론

```kotlin
fun minusNum(a : Int, b : Int) = a - b   // 단일 표현식 함수

println(minusNum(minusNum(1000, 200), 100))   // 700
```
</br>

### 2.2.4 문자열 템플릿

- 변수를 포함해 문자열을 만들 때, 변수명 앞에 $ 붙이기
- 변수가 하나라면 \$, 더 많다면 \${ } 로 감싸주면 됨

```kotlin
val price = 3000
val tax = 300

val originalPrice = "The original price is $price"
val totalPrice = "The total price is ${price + tax}"

println(originalPrice)   // The original price is 3000
println(totalPrice)      // The total price is 3300
```
</br></br></br>

## 2.3 제어문

- 코틀린은 직관적이고 간결한 제어문 제공
</br></br>

### 2.3.1 범위 클래스

- 코틀린을 이용하면 특정 범위의 값들을 간편하게 표현 가능
- 범위 클래스 : IntRange, LongRange, CharRange 등

```kotlin
val numRange : IntRange = 1.5

println(numRange.contains(3))   // true
println(numRange.contains(10))  // false

val charRange : CharRange = 'a'..'e'

println(charRange.contains('b'))   // true
println(charRange.contains('z'))   // false
```
</br></br>

### 2.3.2 for문

- for 문은 대부분의 프로그래밍 언어에서 사용되는 반복문
- 코틀린에서는 in 연산자와 함께 쓰면 훨씬 간편하게 사용 가능
- 1 에서 5 까지 출력

```kotlin
for(i in 1..5) {
	println(i)   // 1, 2, 3, 4, 5
}
```

- 5 에서 1 로 출력할 때, downTo 키워드 이용

```kotlin
for i in 5 downTo 1) {
	println(i)   // 5, 4, 3, 2, 1
}
```

- 1 에서 10 까지 2씩 증가할 때, setp 키워드 사용
step 키워드 뒤의 숫자를 바꿔서 증가 값을 바꿀 수 있음

```kotlin
for(i in 1..10 step 2) {
	println(i)   // 1, 3, 5, 7, 9
}
```

- 배열의 요소 출력

```kotlin
val students = arrayOf("jun-gi", "jun-su", "si-u", "yeon-seo", "jun-seo")

for(name in students) {
	println(name)   // jun-gi, jun-su, si-u, yeon-seo, jun-seo
}
```

- withIndex( ) 함수를 이용하면 요소의 인덱스도 함께 가져올 수 있음

```kotlin
for((index, name) in students.withIndex()) {
	println("Index : $index Name : $name")
}

// Index : 0 Name : jun-gi
// Idnex : 1 Name : jun-su
// Idnex : 2 Name : si-u
// Index : 3 Name : yeon-seo
// Index : 4 Name : jun-seo
```
</br></br>

### 2.3.3 while문

- while 문 : 주어진 조건이 참일 때 계속 반복하는 제어문
- do while 문은 조건과 관계없이 최소 한 번은 실행되는 특징

```kotlin
var num = 1

while(num < 5) {
	prnitln(num)   // 1, 2, 3, 4 까지 출력
	num++          // 1을 더함
}
```

- do while 문은 먼저 실행하고 조건을 판단하여 다시 실행할지 멈출지 결정
→ 최소 1회 실행이 보장된 구문

```kotlin
var num = 1

do {
	num++
	println(num)   // 2, 3, 4, 5 까지 출력
} while (num < 5)
```
</br></br>

### 2.3.4 if문

- if 문은 자바와 같은 방식으로 사용

```kotlin
val examScore = 60
val isPass = false

if(examScore > 80) {
	isPass = true
}

printlnt("시험 결과 : $isPass")   // 시험 결과 : false
```

- if-else 문도 자바와 같음
조건식을 통과하지 못했을 때 실행할 코드를 else 안에 넣어주면 됨

```kotlin
val examScore = 99
if(examScore == 100) {
	println("만점이네요.")
} else {
	println("만점은 아니에요.")
}
```

- if 문을 다음과 같이 표현식으로도 사용 가능
- 일반적인 명령문 (statement) : 어떤 동작 (action) 을 수행
- 표현식 (expression) 은 하나의 값 (value) 으로 평가되는 문장을 말함
⇒ 표현식을 해석하면 결국 값 하나만 남음
- if 문을 활용하여 변수에 값을 할당할 수 있음
- 값을 할당해야 하므로 반드시 else 문 필요

```kotlin
val myAge = 40
val isAdult = if(myAge > 30) true else false

printlnt("성인 여부 : $isAdult")   // 성인 여부 : true
```
</br></br>

### 2.3.5 when문

- 코틀린에는 값에 따라서 분기하여 코드를 실행하는 switch 문이 없음
- 대신 when 문이 있음

```kotlin
val weather = 15

when(weather) {
	-20 -> { println("매우 추운 날씨") }             // 값 하나
	11, 12, 13, 14 -> { println("쌀쌀한 날씨") }     // 값 여러 개
	in 15..26 -> { println("활동하기 좋은 날씨") }   // 범위 안에 들어가는 경우

	// 범위 안에 안 들어가는 경우
	!in -30..50 -> { println("잘못된 값입니다. -30 ~ 50 가운데 값을 적어주세요.") }
	else -> { println("잘 모르겠는 값") }   // 위 경우가 모두 아닐 때
```

- if 문과 마찬가지로 when 문도 값을 반환하는 표현식으로 사용 가능
- 값을 무조건 할당해야 하므로 else 문 필수

```kotlin
val essayScore = 95
vla grade = when(essayScore) {
	in 0//40 -> "D"
	in 41..70 -> "C"
	in 71..90 -> "B"
	else -> "A"
}

println("에세이 학점 : $grade")
```
</br></br></br>

## 2.4 컬렉션

- 자바에서도 리스트 (List), 셋 (Set), 맵 (Map) 등 여러 자료구조 사용
- 코틀린에서도 이러한 컬렉션을 모두 사용할 수 있을 뿐만 아니라 몇 가지 편리한 함수를 추가로 제공
- 코틀린 컬렉션
    - 읽기 전용 (immutable) 컬렉션
    - 읽기-쓰기 (mutable) 컬렉션
</br></br>

### 2.4.1 리스트 (List)

- 리스트 : 순서가 있는 자료구조
- 읽기 전용 리스트/읽기 쓰기 모두 가능한 리스트 정한 후, 목적에 맞는 함수를 사용해 리스트를 만들어야 함
- 읽기 전용 리스트 : listOf( ) 함수 사용

```kotlin
val numImmutableList = listOf(1, 2, 3)
numList[0] = 1   // 오류 발생 - 리스트가 immutable(읽기 전용)이기 때문
```

- 읽기 쓰기 모두 가능한 리스트 : mutableListOf( ) 함수 사용

```kotlin
val numMutableList = mutableListOf(1, 2, 3)
numMutableList[0] = 100      // 첫 번째 요소를 1에서 100으로 변경

println(numMutableList)      // [100, 2, 3]
println(numMutableList[0])   // 100
```

- 리스트에 어떤 요소가 있는지 확인 : contains( ) 함수 사용

```kotlin
println(numMutableList.contains(1))   // false
println(numMutableList.contains(2))   // true
```
</br></br>

### 2.4.2 셋 (Set)

- 셋 (Set) : 순서가 없음, 중복되지 않은 요소들로 만들어지므로 같은 값을 추가하더라도 해당 값은 하나만 저장됨
- 리스트와 마찬가지로 읽기 전용 셋과 읽기 쓰기 모두 가능한 셋 → 2가지 제공
- 읽기 전용 셋 : setOf ( )

```kotlin
val immutableSet = setOf(1, 1, 2, 2, 2, 3, 3)
println(immutableSet)   // [1, 2, 3]
```

- 읽기 쓰기 모두 가능한 셋 : mutableSetOf( )

```kotlin
val mutablSet = nutableSetOf(1, 2, 3, 3, 3, 3)

mutableSet.add(100)      // 100을 추가 -> true
mutableSet.remove(1)     // 1을 제거 -> true
mutableSet.remove(200)   // 200을 제거 -> false

println(mutableSet)   // [2, 3, 100]
println(mutableSet.contains(1))   // 앞에서 1을 제거함 -> false
```
</br></br>

### 2.4.3 맵 (Map)

- 맵은 키와 값을 짝지어 저장하는 자료구조
- 키는 중복되지 않도록 해야 함
- 읽기 전용 맵과, 읽기 쓰기 모두 가능한 맵 → 2가지 종류
- 맵의 요소 : Pair(A, B)를 사용, A to B 로도 표현 가능
- 읽기 전용 맵 : mapOf( )

```kotlin
val immutableMap = mapOf("name" to "junsu", "age" to 13, "age" to 15, "height" to 160)
println(immutableMap)   // {name=junsu, age=15, height=160}
                        // 키는 중복 불가이므로 "age"는 뒤에 정의한 값으로 나옴
```

- 읽기 쓰기 모두 가능한 맵 : mutableMapOf( )

```kotlin
val mutableMap = nutableMapOf("돈까스" to "일식", "짜장면" to "중식", "김치" to "중식")

mutableMap.put("막국수", "한식")     // 새로운 요소 추가 -> null
mutableMap.remove("돈까스")          // key값으로 요소 삭제 -> 일식
mutableMap.replace("김치", "한식")   // 기존 요소 교체 -> 중식

println(mutableMap)   // {짜장면=중식, 김치=한식, 막국수=한식}
```
</br></br></br>

## 2.5 클래스

- 클래스 : 객체를 정의하는 설계도
- 코틀린에서는 자바보다 클래스를 좀 더 편하게 정의 가능
- 자바의 보일러 플레이트 코드 삭제, 필요한 코드만 작성하면 되도록 변경됨
    - 보일러 플레이트 코드 : 상용구 코드, 변형이 거의 또는 전혀 없이 여러 위치에서 반복되는 코드 문구
</br></br>

> **함수 vs 메서드**

함수 : 어떠한 기능을 수행하는 코드 형식
메서드 : 클래스 내부에 선언되어 있는 함수

메서드 역시 함수에 포함되는 개념
> 
</br></br>

### 2.5.1 클래스 선언 및 객체 생성

- class 키워드와 클래스 이름만으로도 클래스를 만들 수 있음
- 아무 기능도 없어 의미가 없음

```kotlin
class Car
```
</br>

- 읽기 전용 프로퍼티 추가
    - 프로퍼티 : 클래스의 속성
- color 가 Car 클래스의 프로퍼티

```kotlin
class Car(val color : String)
```
</br>

- 객체를 생성
- 자바에서는 new 키워드 사용, 코틀린에서는 필요 없음

```kotlin
val car = Car("red")   // 객체 생성
println("My car color is ${car.color}")   // 객체의 color 프로퍼티 사용
```
</br></br>

### 2.5.2 클래스 생성자

- 생성자 : 객체를 생성할 때 항상 실행되는 특수한 함수, 객체 초기화가 목적
- 코틀린의 생성자
    - 주 생성자 (primaty constructor)
    - 보조 생성자 (secondary constructor)
</br></br>

**주 생성자**

- 클래스 이름 옆에 괄호로 둘러쌓인 코드 = 주 생성자

```kotlin
class Person constructor(name : String) { }
```

- 키워드 constructor 생략 가능

```kotlin
class Person(name : String) { }
```

- var, val 이용하면 프로퍼티 선언과 초기화를 한 번에 할 수 있음
- name을 프로퍼티로 선언, 생성 시 입력된 값을 name 프로퍼티에 할당해 줌

```kotlin
class Person(val name : String) { }
```
</br>

**보조 생성자**

- 보조 생성자 : 주 생성자와 다르게 클래스 바디 내부에서 constructor 키워드를 이용해 만듬
- 객체 생성 시 실행할 코드를 작성해 넣을 수 있음

```kotlin
class Person {
	constructor(age : Int) {
		println("I'm $age years old")
	}
}
```

- 주 생성자가 존재할 때는 반드시 this 키워드를 통해 주 생성자를 호출

```kotlin
class Person(name : String) {
	constructor(name : String, age : Int) : this(name) {
		println("I'm $age years old")
	}
}
```
</br>

초기화 블록

- 초기화 블록 : 객체 생성 시 필요한 작업을 하는 것
- init{ } 안의 코드들은 객체 생성 시 가장 먼저 실행되고 주 생성자의 매개변수를 사용할 수 있음
- 주로 주 생성자와 같이 쓰임

```kotlin
class Person(name : String) {
	val name : String
	init {
		if(name.isEmpty()) {   // 문자열이 비어 있는 경우 에러 발생
			throw IllegalArgumentException("이름이 없어요.")
		}
		this.name = name   // 문자열이 안 비어 있으면 이름 저장
	}
}
```

- Person 클래스는 이름을 인수로 받으며, 이름이 비어 있으면 에러를 발생시킴

```kotlin
val newPerson = Person("")   // 결과가 보이지 않고, IllegalArgumentException 에러 발생
```

- 이름을 가진 Person 객체 생성

```kotlin
val newPerson = Person("yeon-seo")   // 객체 생성 성공
```

- 초기화 블록을 통해 객체 생성 시 필요한 작업을 할 수 있음
</br></br></br>

### 2.5.3 클래스의 상속

- 코틀린에서 클래스를 상속 받으려면 부모 클래스에 open 키워드를 추가해야 함
- 메서드도 자식 클래스서 오버라이드하려면 부모 클래스의 메서드에 open 키워드를 추가해야 함
    - 메서드 오버라이드 (Method Override) : 메서드 재정의, 조상의 메서드를 자식 클래스에서 재정의하는 기법
- 콜론 : 을 이용해 상속을 나타냄

```kotlin
open class Flower {
	open fun waterFlower() {
		println("water flower")
	}
}

class Rose : Flower() {
	override fun waterFlower() {
		super.waterFlower()   // Flower 클래스의 메서드 먼저 실행
		println("Rose is happy now")
	}
}

val rose = Rose()   // 객체 생성
rose.waterFlower()  // water flower; Rose is happy now
```

- 부모 클래스 생성자를 실행시키려면 자식 클래스에서 반드시 부모 클래스의 생성자를 명시적으로 호출해줘야 함

```kotlin
open class Flower(val name : String) { }

class Rose(name : String, color : String) : Flower(name) { }
```
</br></br>

### 2.5.4 접근 제한자

- 접근 제한자 : “누구에게 클래스의 메서드와 변수를 공개할 것인가?” 를 정하는 것
- 코틀린 클래스의 기본 속성과 메서드 : public (퍼블릭)
- public 이외에도, private (프라이빗), protected (프로텍티드), internal (이터널) 접근 제한자가 있음
- 접근 제한자가 공개하는 범위
    - public : 코틀린의 기본 접근 제한자, 어디에서나 접근 가능
    - internal : 같은 모듈 내에서 접근 가능, 안드로이드 개발 시에는 한 프로젝트 안에 있으면 같은 모듈이라고 보면 됨, 한 프로젝트에 여러 모듈을 만든다면 이는 모듈 간 접근이 제한됨
    - protected : 자식 클래스에서는 접근 가능
    - private : 해당 클래스 내부에서만 접근 가능
- 접근 제한자는 변수나 메서드 앞에 써 주면 됨

```kotlin
private val b = 2
```
</br></br>

### 2.5.5 컴패니언 객체 : companion 키워드

- 자바에서 static 키워드를 이용하면 정의된 변수나 메서드들은 객체를 만들지 않고도 접근이 가능
- 코틀린에서는 companion 키워드가 그 역할을 함

```kotlin
class Dinner {
	companion object {     // object 키워드
		val MENU = "pasta"   // 정적 변수 생성
		fun eatDinner() {    // 정적 메서드 생성
			println("$MENU is yummy!")
		}
	}
}

println(Dinner.Companion.MENU)   // pasta
println(Dinner.MENU)   // Companion 생략 가능 -> pasta
Dinner.eatDinner()     // pasta is yummy!
```

- Dinner 클래스의 객체를 생성하지 않고도 companion으로 정의된 MENU 변수와 eatDinner() 메서드를 사용하는 데 성공
- object 키워드를 사용해서 만들어진 객체는 여러 번 호출되더라도 딱 하나의 객체만 생성되어 재사용됨
</br></br></br>

### 2.5.5 추상 클래스

- 추상 클래스 : 그 자체로는 객체를 만들 수 없는 클래스, 일반적으로 추상 메서드가 포함된 클래스
- 추상 메서드 : 아직 구현되지 않고 추상적으로만 존재하는 메서드
- 추상 클래스와 추상 메서드 앞에 abstract 키워드 붙임
- 상속 받는 자식 클래스에 특정 메서드 구현을 강제하고 싶을 때 씀
- 추상 클래스 자체로는 직접 객체를 만들 수 없고, 자식 클래스에서 추상 메서드를 구현한 다음, 자식 클래스의 객체를 생성하면 됨
    - ex) 게임은 실체(객체)가 없지만 오버워치는 실체(객체)가 있음
    → 게임 = 추상 클래스, 오버워치 = 추상 클래스를 상속 받은 클래스

```kotlin
abstract class Game {
	fun startCame() {
		println("게임을 시작했습니다.")
	}

	// 추상 메서드
	abstract fun printName()
}

class Overwatch : Game() {
	override fun printName() {   // 추상 메서드 구현
		println("오버워치입니다.")
	}
}

val overwatch = Overwatch()
overwatch.startGame()   // Game 클래스 메서드 -> 게임을 시작했습니다.
overwatch.printName()   // Overwatch 클래스에서 구현한 메서드 -> 오버워치입니다.
```
</br></br>

### 2.5.7 데이터 클래스

- 데이터 클래스 : 특정한 메서드의 실행보다는 데이터 전달에 목적
- 데이터 전달용 객체 (data transfer object) 를 간편하게 생성하도록 data class 라는 키워드를 제공
- 주 생성자에는 반드시 val 이나 var 를 사용한 프로퍼티 정의가 적어도 하나 이상 필요
val, var 가 아닌 매개변수는 사용할 수 없음

```kotlin
data class Memo(val title : String, val content : String, var isDone : Boolean)

var memo1 = Memo("마트 가기", "계란, 우유, 빵", false)
```

- 데이터 클래스는 각각의 프로퍼티에 대한 toString( ), copy( )와 같은 메서드를 자동으로 만들어 줌
    - toString( ) : 객체에 포함되어 있는 데이터를 출력하여 볼 수 있음, 생성자에 포함된 프로퍼티만 출력됨
    - copy( ) : 객체의 속성들을 복사하여 반환하는 메서드, 인수로 받은 프로퍼티만 해당 값으로 바뀌어 복사해 줌

```kotlin
data class Memo(val title : String, val content : String, var isDone : Boolean)

var memo1 = Memo("마트 가기", "계란, 우유, 빵", false)
var memo2 = memo1.copy(content = "칫솔, 과자")   // content 프로퍼티만 변경

println(memo1.toString())
// Memo(title=마트가기, content=계란, 우유, 빵, isDone=false)

println(memo2.toString())
// Memo(title=마트가기, content=칫솔, 과자, isDone=false)
```
</br></br></br>
## 2.6 인터페이스

- 인터페이스 : 클래스들이 같은 기능을 수행하게끔 강제하는 것
- 예) 자동차 인터페이스 : 최소한 가고(drive) 멈추는(stop) 기능을 추상 메서드로 만들어야 함
자동차 인터페이스를 구현하는 모든 클래스는 반드시 drive( ) 와 stop( ) 을 오버라이드하여 구현 해야 함
</br></br>

### 2.6.1 인터페이스 정의

- interface 키워드 이용하면 인터페이스 만들 수 있음
- 추상 메서드임을 선언하는 키워드 abstract는 추상 클래스에서는 필요하나 인터페이스에서는 생략 가능

```kotlin
interface Car {
	abstract fun drive()
	fun stop()   // abstract 생략 가능
}
```
</br>

### 2.6.2 디폴트 메서드

- 자바에 default 메서드가 있는 것처럼, 코틀린도 인터페이스에서 기본적으로 구현하는 메서드를 제공 가능
- 인터페이스를 구현하는 클래스들은 디폴트 메서드만큼은 오버라이드하지 않아도 됨
(필요하면 오버라이드할 수도 있음)
- 특별한 키워드 없이 디폴트 메서드를 구현해 주면 됨

```kotlin
interface Car {
	abstract fun drive()
	fun stop()   // abstract 생략 가능
	fun destory() = println("차가 파괴되었습니다.")   // 디폴트 메서드
}
```
</br>

### 2.6.3 인터페이스의 구현

- 인터페이스를 구현 = 클래스명 다음 콜론 : 뒤에 인터페이스 이름을 쓰면 됨

```kotlin
class Ferrari : Car {
}
// 에러 - 인터페이스에 선언된 추상 메서드를 구현하지 않았기 때문

class Ferrari : Car {
	override fun drive() {
		println("페라리가 달립니다.")
	}

	override fun stop() {
		println("페라리가 멈춥니다.")
	}
}
```

- Car 인터페이스를 구현한 Ferrari 클래스로 객체를 생성하고 사용

```kotlin
cal myFerrari = Ferrari()   // 객체 생성

myFerrari.drive()     // 페라리가 달립니다.
myFerrari.stop()      // 페라리가 멈춥니다.
myFerrari.destory()   // Car 인터페이스 디폴트 메서드 -> 차가 파괴되었습니다.
```
</br>

### 2.6.4 다중 인터페이스 구현

- 한 클래스에서 클래스는 단 한 개만 상속 받을 수 있음
- 인터페이스는 2개 이상 구현할 수 있음

```kotlin
interface Animal {
	fun breath()
	fun eat()
}

interface Human {
	fun think()
}

class Korean : Animal, Human {   // 2개 이상의 인터페이스 구현
	override fun breath() {
		println("후-하 후-하")
	}

	override fun eat() {
		println("한식 먹기")
	}

	override fun think() {
		println("생각하기")
	}
}
```
</br>

### 2.6.5 클래스 상속과 인터페이스 구현

- [[2.6.4]](https://www.notion.so/Chapter-02-034aee59dfeb474488f929520d4bca25)의 다중 인터페이스 구현 예제에 추가로 Name 클래스 상속
- 코틀린은 다중 상속을 지원하지 않으므로 단 한 개의 클래스만 상속 가능
- 부모 클래스 생성자에 필요한 인수는 자식 클래스 생성자에서 전달해야 함

```kotlin
interface Animal {
	fun breath()
	fun eat()
}

interface Human {
	fun think()
}

open class Name(val name : String) {
	fun printName() {
		println("제 이름은 $name")
}

class Korean(name : String) : Name(name), Animal, Human {   // 부모 클래스 생성자에 필요한 인수 전달
	override fun breath() {
		println("후-하 후-하")
	}

	override fun eat() {
		println("한식 먹기")
	}

	override fun think() {
		println("생각하기")
	}
}

val joyce = Korean("정아")
joyce.breath()      // 후-하 후-하
joyce.printName()   // 제 이름은 정아
```
</br></br></br>

## 2.7 Null 처리하기

- 자바에서는 객체를 반환하는 함수가 반환할 객체가 없을 때 null 반환
→ 이 방식은 null 체크가 필요
→ 안 그러면 NullPointerException을 만나게 될지도 모름
- NPE 에러가 코틀린에서 안전하게 해결됨
</br></br>

### 2.7.1 Nullable과 Non-Nullable

- 코틀린은 기본적으로 ‘객체는 null이 될 수 없다’고 봄
- 모든 객체들은 생성과 동시에 초기화해야 함

```kotlin
var myName : String   // 초기화를 해 주지 않아 에러
```

- 변수를 null 로 초기화
- null 이 될 수 없는 String 형에 null 을 넣어서 에러

```kotlin
var myName : String = null   // non-nullable 자료형에 null을 넣어서 에러
```

- 컴파일러에게 null 값이 허용된다고 알려주는 방법
⇒ 자료형 뒤에 ? 기호를 붙여, 명시적으로 null이 올 수 있음을 알려주면 됨
- Int 가 non-nullable (null 불가)이라면 Int? 는 nullable (null 가능)

```kotlin
var myName : String? = null
```

- myName은 null 이 가능한 String 형이 됨

```kotlin
var myName : String? = null
myName = "Joyce"
println(myName.reversed())   // 에러 발생
```

- 에러가 발생하는 이유 : myName이 null 이 될 수도 있기 때문에, 코틀린 컴파일러가 개발자에게 확인하라고 알려서
⇒ 실제 실행하고 난 후 런타임 오류 (NullPointerException) 가 나기 전에 코드 작성 시 미리 에러를 예방할 수 있음
- 코드가 복잡해지면 실수로 null 이 들어갈 가능성이 높아짐
- 코틀린은 컴파일 시점에 null 가능성을 체크하여 오류에 안전한 방어적인 코드를 작성하도록 유도
</br></br>

### 2.7.2 셰이프 콜 연산자 ?

- ? 연산자를 이용 : 메서드 호출, 혹은 객체 프로퍼티 접근과 null 체크를 한 번에 할 수 있음
- ? 연산자 = 셰이프 콜 연산자 (안전 호출 연산자)
- 객체 참조가 null 이면, 셰이프 콜 연산자의 반환값은 null

```kotlin
fun reverseName(name : String?) : String? {   // 인수, 반환값 자료형 모두 null 가능
	return name?.reversed()   // name이 null이라면 null을 반환
}

println(reverseName("joyce"))   // ecyoj
println(reverseName(null))      // null
```

- myName 변수가 null 이 아닌 경우에만 함수 실행
</br></br>

### 2.7.3 엘비스 연산자 ?:

- 엘비스 연산자 : ? 연산자를 이용해 셰이프 콜을 할 시 null 을 반환하지 않고, 기본값을 반환

```kotlin
fun reverseName(name : String?) : String {   // 반환값 자료형은 null 불가능
	return name?.reversed() ?: "이름을 확인해주세요."  // null일 때 기본값 반환
}

println(reverseName("joyce"))   // ecyoj
println(reverseName(null))      // 이름을 확인해주세요.
```
</br>

> **왜 엘비스 연산자?    ?:**
고개를 왼쪽으로 꺾고 보면
엘비스 프레슬리의 시그니처 헤어 ? + 엘비스의 두 눈 :
> 
</br>

### 2.7.4 확정 연산자 !!

- 확정 연산자 !! : 절대 null 이 아님을 보증
- 컴파일러에게 “이게 null 이 가능한 자료형이긴 한데, 절대 null 아니니까 걱정마!!” 라고 알리는 것

```kotlin
fun reverseName(name : String?) : String { // 반환 자료형은 null 불가능
	return name!!.reversed()   // 절대 null이 아님을 보증
}
```

- null 이 아닌 값을 함수의 인수로 넣으면 문제가 일어나지 않음

```kotlin
println(reverseName("joyce"))   // ecyoj
```

- null 을 넣으면 바로 NullPointerException 에러가 발생

```kotlin
println(reverseName(null))   // 에러 발생, java.lang.NullPointerException
```

- !! 연산자를 남용하면 코틀린이 제공하는 자료형 안전성을 제대로 누리는 것이 아님
- 편리하지만 무분별한 사용은 금물
</br></br>

### 2.7.5 lateinit 키워드와 lazy 키워드

- lateinit : var 로 선언한 변수의 늦은 초기화를 도움
- lazy : val 로 선언한 상수의 늦은 초기화를 도움
</br></br>

**lateinit 키워드**

- 코틀린에서는 기본적으로 모든 변수는 null이 아니기 때문에, 반드시 선언과 동시에 초기화해야 함
- 변수에 값을 나중에 넣고 싶으면 → lateinit 키워드
- lateinit 키워드 사용 : 변수를 선언하고 나중에 값을 할당할 수 있음

```kotlin
lateinit var lunch : String   // lateinit 키워드로 나중에 값 할당할 것임을 선언
lunch = "waffle"

println(lunch)   // waffle
```

- lateinit 키워드를 사용할 때 주의점
    1. var 변수에서만 사용
    2. nullable 자료형과 함께 사용할 수 없음
    3. 초기화 전에 변수를 사용하면 에러 발생
    4. 원시 자료형 (Int, Double, Float) 등에는 사용 불가능
    5. **::변수명.isInitialized( )** 함수로 함수가 초기화되었는지 확인 가능
</br></br>

**lazy 키워드**

- lazy 키워드 사용 : 변경할 수 없는 변수인 val의 늦은 초기화를 할 수 있음
- 객체가 생성될 때 초기화되는 것이 아니라 처음 호출될 때 lazy{ } 안의 코드가 실행되면서 초기화됨

```kotlin
val lazyBear : String by lazy {
	println("곰이 일어났습니다.")
	"bear"
}

println(lazybear)   // lazy 블록 실행됨, 곰이 일어났습니다; bear
println(lazybear)   // 이미 초기화되었으므로 블록 실행 안 됨, bear
```
</br></br></br>

## 2.8 람다식

- 람다식 (Lambda Expression) : 람다 표현식, 람다라고도 불림
- 람다식 = “마치 값처럼 다룰 수 있는 익명 함수”
- 익명 함수 : 이름이 없이 정의되는 함수

```kotlin
val sayHello = fun() { println("안녕하세요.") }   // sayHello 변수에 할당되는 함수 = 익명 함수
sayHello()   // 안녕하세요.
```

- 람다식을 값처럼 다룰 수 있음 = 람다식 자체가 함수의 인수가 되거나 변환값이 될 수 있음
</br></br>

### 2.8.1 람다식 정의

- 람다를 이용, 인수 숫자의 제곱값을 반환

```kotlin
val squareNum : (Int) -> (Int) = { number -> number*number }
println(squareNum(12))   // 144
```

- 람다의 기본 정의 방법
    - squareNum (변수명) : 람다식을 저장할 변수의 이름 지정
    - (Int) (인수 자료형) : 람다식의 인수 자료형 지정
    - (Int) (반환형) : 람다식의 반환 자료형 지정
    - number (인수) : 인수 목록을 나열
        - number의 자료형은 인수 자료형에서 명시해 주었으므로 형추론이 되어 Int가 됨
    - number*number (실행할 코드) : 람다식에서 실행할 코드
- 자료형은 인수에서 명시해 주어도 됨
- 어디든 한 곳에서는 명시를 해야 함

```kotlin
val squareNum2 = { number : Int -> number*number }
```

- 람다식의 인수가 한 개이면 인수를 생략하고 it으로 지칭할 수 있음

```kotlin
val squareNum3 : (Int) -> Int = {it*it} 
```
</br></br>

### 2.8.2 람다를 표현하는 다양한 방법

- 람다 : “값처럼” 사용할 수 있는 익명 함수
- 값처럼 사용 : 함수의 인수로도 넣어줄 수 있다는 말

```kotlin
fun invokeLambda (lambda : (Int) -> Boolean) : Boolean {   // 람다식을 인수로 받음
	return lambda(5)
}
```

- 람다식을 인수로 넣어 사용 가능

```kotlin
val paramLambda : (Int) -> Boolean = { num -> num == 10 }

println(invokeLambda(paramLambda)) // 람다식의 인수로 넣은 5 != 10 이므로 -> false
```

- 변수를 사용하지 않고 바로 넣어줄 수 있음

```kotlin
invokeLambda({ num -> num == 10 })   // 람다식 바로 넣어주기
invokeLambda({ it == 10 })           // 인수가 하나일 때 it으로 변경 가능
invokeLambda() { it == 10 }          // 만약 함수의 마지막 인수가 람다일 경우, 밖으로 뺄 수 있음
invokeLambda{ it == 10 }             // 그 외 인수가 없을 때 ( ) 생략 가능
```
</br></br>

### 2.8.3 SAM (Single Abstract Method) 변환

- 안드로이드 개발을 하다 보면 다음과 같은 코드를 아주 많이 작성하게 됨

```kotlin
button.setOnClickListener {
	// 버튼이 눌렀을 때 작동할 코드
}
```

- [[2.8.2]](https://www.notion.so/Chapter-02-034aee59dfeb474488f929520d4bca25)에서 배운 대로 함수의 마지막 인수가 람다식인 경우, ( ) 를 생략하고 { } 에 코드를 작성 가능
- setOnClickListener( ) 함수도 마지막 인수가 람다식? → NO
- setOnClickListener 는 람다식이 아닌 OnClickListener 인터페이스를 인수로 받음

```kotlin
public void setOnClickListener(@Nullable OnClickListner l) {
	...
}
```

- OnClickListener 는 다음과 같이 추상 메서드 하나가 있는 인터페이스

```kotlin
public interface OnClickListener {
	void onClick(Viewv);
}
```

- setOnClickListener 는 람다식이 아님에도 람다식처럼 취급됨
- 가능한 이유 : 자바 8에서 소개된 SAM 변환
- SAM 변환 조건
    1. 코틀린 인터페이스가 아닌 자바 인터페이스여야 할 것
    2. 인터페이스 내에는 딱 한 개의 추상 메서드만 존재할 것
- 조건을 만족하는 경우, 익명 인터페이스 객체 생성에 람다식 사용 가능
- 람다식을 사용하면 코드가 훨씬 간결해지고 가독성이 높아짐

```kotlin
// 람다식을 사용하지 않았을 때
button.setOnClickListener(object : OnClickListener {
	override fun onClick(view: View) {
		doSomething()
	}
})

// 람다 사용 1
button.setOnClickListner({ view -> doSomething() })

// 람다 사용 2 : 코틀린 컴파일러가 인수를 미리 알기 때문에 생략
// (인수가 여러 개 일 때) 마지막 인수가 람다인 경우 { }를 밖으로 이동
button.setOnClickListener() { doSomething() }

// 람다 사용 3 : 유일한 인수인 경우 () 생략
button.setOnClickListner { doSomething() }
```