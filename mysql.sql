- 테이블 생성
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

- 환전 요청 시
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
----
insert 
    into
        exchange
        (amount_after_exchange, amount_in_krw, created_at, currency_id, modified_at, status, user_id) 
    values
        (?, ?, ?, ?, ?, ?, ?)

- 특정 유저의 환전 요청 조회
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
----
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

- 특정 환전 요청의 상태 변경
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
----
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

- 유저 삭제
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
----
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


- 특정 고객 환전 요청 정보 조회
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

----
    select
        count(e1_0.id),
        sum(e1_0.amount_in_krw) 
    from
        exchange e1_0 
    where
        e1_0.user_id=? 
    group by
        e1_0.user_id

   