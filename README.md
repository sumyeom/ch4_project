# 환율 앱을 만들어보자!!

Spring을 이용하여 환율을 요청하는 앱 서버 구현

---
### 요구사항
* Level1
  * 환전 요청 중간 테이블 생성
  * 고객과 통화 테이블 간 연관관계 설정
    
* Level2
  * 환전 CRUD(Exchange API 참고)

* Level3
  * 예외처리
 
* Level4
  * PostConstruct 적용하여 환율 유효한지 확인

* Level5
  * JPQL 활용하여 고객의 모든 환전 요청을 그룹화하여 조회

* Level6
  * 달러 이외의 통화를 환전할 수 있도록 수정
---
### API 명세서
## 🗒️ [Exchange API](https://sixth-question-fbd.notion.site/14c9c26ea5c6801cb8a8c679d042b30a?v=ce0859b76e5447b9add220f299c1c5ac)

---
### ERD
![image](https://github.com/user-attachments/assets/2b751a0d-5653-481d-9d68-ce6005fc734e)

---
### SQL
- 테이블 생성
  ~~~sql
    create table currency (
        exchange_rate decimal(38,2),
        created_at datetime(6),
        id bigint not null auto_increment,
        modified_at datetime(6),
        currency_name varchar(255),
        symbol varchar(255),
        primary key (id)
    ) engine=InnoDB

    create table user (
        created_at datetime(6),
        id bigint not null auto_increment,
        modified_at datetime(6),
        email varchar(255),
        name varchar(255),
        password varchar(255),
        primary key (id)
    ) engine=InnoDB

    create table exchange (
        amount_after_exchange decimal(38,2) not null,
        amount_in_krw decimal(38,2) not null,
        created_at datetime(6),
        currency_id bigint,
        id bigint not null auto_increment,
        modified_at datetime(6),
        user_id bigint,
        status varchar(10) not null,
        primary key (id)
    ) engine=InnoDB
  ~~~

- 환전 요청 시
  ~~~sql
    select
        c1_0.id,
        c1_0.created_at,
        c1_0.currency_name,
        c1_0.exchange_rate,
        c1_0.modified_at,
        c1_0.symbol 
    from
        currency c1_0 
    where
        c1_0.currency_name=?
  ~~~
~~~sql
insert 
    into
        exchange
        (amount_after_exchange, amount_in_krw, created_at, currency_id, modified_at, status, user_id) 
    values
        (?, ?, ?, ?, ?, ?, ?)
~~~

- 특정 유저의 환전 요청 조회
~~~sql
    select
        e1_0.id,
        e1_0.amount_after_exchange,
        e1_0.amount_in_krw,
        e1_0.created_at,
        e1_0.currency_id,
        e1_0.modified_at,
        e1_0.status,
        e1_0.user_id 
    from
        exchange e1_0 
    where
        e1_0.user_id=?
~~~
~~~sql
    select
        c1_0.id,
        c1_0.created_at,
        c1_0.currency_name,
        c1_0.exchange_rate,
        c1_0.modified_at,
        c1_0.symbol 
    from
        currency c1_0 
    where
        c1_0.id=?
~~~
- 특정 환전 요청의 상태 변경
~~~sql
    select
        e1_0.id,
        e1_0.amount_after_exchange,
        e1_0.amount_in_krw,
        e1_0.created_at,
        c1_0.id,
        c1_0.created_at,
        c1_0.currency_name,
        c1_0.exchange_rate,
        c1_0.modified_at,
        c1_0.symbol,
        e1_0.modified_at,
        e1_0.status,
        u1_0.id,
        u1_0.created_at,
        u1_0.email,
        u1_0.modified_at,
        u1_0.name,
        u1_0.password 
    from
        exchange e1_0 
    left join
        currency c1_0 
            on c1_0.id=e1_0.currency_id 
    left join
        user u1_0 
            on u1_0.id=e1_0.user_id 
    where
        e1_0.id=?
~~~
~~~sql
    update
            exchange 
        set
            amount_after_exchange=?,
            amount_in_krw=?,
            currency_id=?,
            modified_at=?,
            status=?,
            user_id=? 
        where
            id=?
~~~

- 유저 삭제
~~~sql
    select
        e1_0.user_id,
        e1_0.id,
        e1_0.amount_after_exchange,
        e1_0.amount_in_krw,
        e1_0.created_at,
        c1_0.id,
        c1_0.created_at,
        c1_0.currency_name,
        c1_0.exchange_rate,
        c1_0.modified_at,
        c1_0.symbol,
        e1_0.modified_at,
        e1_0.status 
    from
        exchange e1_0 
    left join
        currency c1_0 
            on c1_0.id=e1_0.currency_id 
    where
        e1_0.user_id=?
~~~
~~~sql
    delete 
    from
        exchange 
    where
        id=?

    delete 
    from
        user 
    where
        id=?
~~~

- 특정 고객 환전 요청 정보 조회
~~~sql
    select
        u1_0.id,
        u1_0.created_at,
        u1_0.email,
        u1_0.modified_at,
        u1_0.name,
        u1_0.password 
    from
        user u1_0 
    where
        u1_0.id=?
~~~
~~~sql
    select
        count(e1_0.id),
        sum(e1_0.amount_in_krw) 
    from
        exchange e1_0 
    where
        e1_0.user_id=? 
    group by
        e1_0.user_id

~~~
---
### Troble Shooting
#### [Exchange Project] (https://withsumyeom.tistory.com/entry/Trouble-Shooting)
---
### 개발 환경
- JDK 17
- intelliJ IDEA 2024.2.3
- window11
- Spring boot 3.3.5
- gradle 8.10.2


