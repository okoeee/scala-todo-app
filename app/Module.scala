import com.google.inject.AbstractModule
import domain.repository.{
  GroupRepository,
  TodoRepository,
  UserRepository,
  UserSessionRepository
}
import infrastructure.repositoryimpl.{
  GroupRepositoryImpl,
  TodoRepositoryImpl,
  UserRepositoryImpl,
  UserSessionRepositoryImpl
}

class Module extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[GroupRepository]).to(classOf[GroupRepositoryImpl])
    bind(classOf[UserRepository]).to(classOf[UserRepositoryImpl])
    bind(classOf[UserSessionRepository]).to(classOf[UserSessionRepositoryImpl])
    bind(classOf[TodoRepository]).to(classOf[TodoRepositoryImpl])
  }
}
