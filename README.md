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

> 1. tableName을 지정하지 않으면 클래스의 이름이 테이블 이름이 된다.   
> 2. room 항목에 자동id를 할당하려면 @PrimaryKey(autoGenerate = true) 속성을 사용한다.   


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

> 1. 데이터베이스에 관한 추상 액세스를 제공하는 메소드가 포함되어 있으며 Room의 주요 구성요소를 형성한다.    
> 2. 첫번째 함수는 사용자를 추가, 두번째는 사용자 업데이트, 세번째는 저장된 사용자 삭제, 마지막은 사용자의 이름은 오름차순으로 정렬한다.   
> 3. onConflict = OnConflictStrategy 종류가 몇가지 있다.   
  IGNORE은 동일한 이름을 무시하고, REPLACE는 Insert할 때 PrimaryKey가 겹치는 것이 있으면 덮어 쓴다는 뜻이다.
> 4. 데이터가 변경 될 때 반응 할 수 있도록 LiveData를 사용해 쉽게 해결한다.


* Room 만들기
<pre>
<code>
@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao() : UserDao
    companion object {
        @Volatile
        private var INSTANCE : UserDatabase? = null
        fun getDatabase(context: Context) : UserDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "user_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
</code>
</pre>

> 1. room의 데이터베이스 클래스는 abbstract이고, @Database에 entities와 버전번호를 설정한다.   
> 2. 싱글톤을 반환하므로 데이터베이스의 다중 인스턴스를 방지한다.   

* Repository 만들기
<pre>
<code>
class UserRepository(private val userDao : UserDao) {
    val readAllData : LiveData<List<User>> = userDao.readAllData()
    suspend fun addUser(user : User) {
        userDao.addUser(user)
    }
    suspend fun updateUser(user: User) {
        userDao.updateUser(user)
    }
    suspend fun deleteUser(user: User) {
        userDao.deleteUser(user)
    }
    suspend fun deleteAllUsers() {
        userDao.deleteAllUsers()
    }
}
</code>
</pre>

> 1. userDao는D 데이터베이스가 아닌 저장소의 생성자로 전달된다.   
> 2. DB의 데이터를 가져오면 LiveData가 캐치해서 데이터가 변경되면 메인 스레드에 알려준다.   

* ViewModel 만들기
<pre>
<code>
class UserViewModel(application: Application) : AndroidViewModel(application) {

    val readAllData : LiveData<List<User>>
    private val repository : UserRepository

    init {
        val userDao = UserDatabase.getDatabase(
            application
        ).userDao()
        repository = UserRepository(userDao)
        readAllData = repository.readAllData
    }

    fun addUser(user : User) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(user)
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateUser(user)
        }
    }

    fun deleteUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteUser(user)
        }
    }

    fun deleteAllUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllUsers()
        }
    }
}
</code>
</pre>

> 1. application을 매개변수로 받고, AndroidViewModel은 상속을 받는다.   
> 2. 멤버변수 추가를 위해 LiveData 사용자 목록을 가져오기 위해 readAllData를 생성했다.   
> 3. 초기화를 통해 userDao에 대한 참조 데이터를 가져온다.   

---------------------------------------------------------------------------

* 결과화면   

<p float="center">
<img src="https://user-images.githubusercontent.com/71965874/112241446-9996b400-8c8d-11eb-9f88-69e29884e427.jpg" width="25% height="15%">
<img src="https://user-images.githubusercontent.com/71965874/112241450-9b607780-8c8d-11eb-9a88-b829d0ddd431.jpg" width="25% height="15%">   
</p>

<p float="center">
<img src="https://user-images.githubusercontent.com/71965874/112241451-9bf90e00-8c8d-11eb-82c2-9dc4ed8861e7.jpg" width="25% height="15%">
<img src="https://user-images.githubusercontent.com/71965874/112241454-9c91a480-8c8d-11eb-891e-e62d6ebabec9.jpg" width="25% height="15%">   
</p>

<p float="center">
<img src="https://user-images.githubusercontent.com/71965874/112241455-9c91a480-8c8d-11eb-8494-806e6cba7eb8.jpg" width="25% height="15%">
<img src="https://user-images.githubusercontent.com/71965874/112241459-9dc2d180-8c8d-11eb-8bd8-21409d196be2.jpg" width="25% height="15%">   
</p>
