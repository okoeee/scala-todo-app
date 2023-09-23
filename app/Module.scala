import com.google.inject.AbstractModule
import domain.repository.{TodoRepository, UserRepository, UserSessionRepository}
import infrastructure.repositoryimpl.{
  TodoRepositoryImpl,
  UserRepositoryImpl,
  UserSessionRepositoryImpl
}

class Module extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[UserRepository]).to(classOf[UserRepositoryImpl])
    bind(classOf[UserSessionRepository]).to(classOf[UserSessionRepositoryImpl])
    bind(classOf[TodoRepository]).to(classOf[TodoRepositoryImpl])
  }
}
