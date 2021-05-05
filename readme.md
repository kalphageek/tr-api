### Tr API 서비스
> api를 통해 받은 데이터를 e-Hub DB에 저장하고, Kafka의 Topic으로 Pub 한다.

### 양방향 관계에서 infinite recursion 해결법
> @JsonIdentityInfo 추가 
```java
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class Eqp1TrDet extends CreatedBaseEntity { 
}

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class Eqp1Tr extends CreatedBaseEntity { 
}
```

### 1:n 객체 저장하기
1. Master의 @OneToMany에 cascade추가
```java
@OneToMany(mappedBy = "eqp1Tr", cascade = CascadeType.ALL)
List<Eqp1TrDet> eqp1TrDets;
```
2. Master에 Detail을 위한 get method생성
```java
public List<Eqp1TrDet> getEqp1TrDets() {
    if (eqp1TrDets == null)
        eqp1TrDets = new ArrayList<>();
    return eqp1TrDets;
}
```
3. Service에 Master에 대한 save호출 (Detail은 save하지 않음)
```java
trRepository.save(eqp1Tr)
```
4. DataSourceBuilder 를 이용해 생성할 때
    * url -> jdbcUrl 
   
5. Test 데이터
```json
{
    "name" : "lot2",
    "value" : "123454",
    "eventTime" : "2021-05-05T00:11:41.4235126",
    "eqp1TrDetDtos" : [
        {
            "col1" : "col1",
            "col2" : "837466"
        },
        {
            "col1" : "col2",
            "col2" : "66"
        }
    ]
}
```