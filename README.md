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

> tableName을 지정하지 않으면 클래스의 이름이 테이블 이름이 된다.   
> room 항목에 자동id를 할당하려면 @PrimaryKey(autoGenerate = true) 속성을 사용한다.   


* Dao 만들기
<pre>
<code>
@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user : User)

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("DELETE FROM user_table")
    suspend fun deleteAllUsers()

    @Query("SELECT * FROM user_table ORDER BY id ASC")
    fun readAllData() : LiveData<List<User>>

}
</code>
</pre>

> 데이터베이스에 관한 추상 액세스를 제공하는 메소드가 포함되어 있으며 Room의 주요 구성요소를 형성한다. 
> 첫번째 함수는 사용자를 추가, 두번째는 사용자 업데이트, 세번째는 저장된 사용자 삭제, 마지막은 사용자의 이름은 오름차순으로 정렬한다.
> onConflict = OnConflictStrategy 종류가 몇가지 있다.
> IGNORE은 동일한 이름을 무시하고, REPLACE는 Insert할 때 PrimaryKey가 겹치는 것이 있으면 덮어 쓴다는 뜻이다.

