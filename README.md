# RoomDatabase

@Entity : Room 작업시 데이터베이스 테이블을 설명한다.   
@Dao : 데이터 접근 객체, SQL 쿼리를 함수에 매핑한다. 사용할 경우 함수를 부르고 나머지는 room에서 처리한다.   
@Room : SQLite 데이터베이스에 대한 지점 역할을 한다.   
Repository : 여러 데이터 소스를 관리한다.


* Entity 만들기
<pre>
<code>
@Parcelize
@Entity(tableName = "user_table")
data class User(
     @PrimaryKey(autoGenerate = true)
     val id : Int,
     val firstName : String,
     val lastName : String,
     val age: Int
) : Parcelable
</code>
</pre>

tableName을 지정하지 않으면 클래스의 이름이 테이블 이름이 된다.   
room 항목에 자동id를 할당하려면 @PrimaryKey(autoGenerate = true) 속성을 사용한다.   
