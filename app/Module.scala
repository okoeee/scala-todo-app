import com.google.inject.AbstractModule
import domain.repository.{TodoRepository, UserRepository}
import infrastructure.repositoryimpl.{TodoRepositoryImpl, UserRepositoryImpl}

class Module extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[UserRepository]).to(classOf[UserRepositoryImpl])
    bind(classOf[TodoRepository]).to(classOf[TodoRepositoryImpl])
  }
}
