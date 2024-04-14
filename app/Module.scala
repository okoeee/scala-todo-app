import com.google.inject.AbstractModule
import domain.repository.{
  GroupMembershipRepository,
  GroupRepository,
  TodoRepository,
  UserRepository,
  UserSessionRepository
}
import infrastructure.repositoryimpl.{
  GroupMembershipImpl,
  GroupRepositoryImpl,
  TodoRepositoryImpl,
  UserRepositoryImpl,
  UserSessionRepositoryImpl
}

class Module extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[GroupRepository]).to(classOf[GroupRepositoryImpl])
    bind(classOf[GroupMembershipRepository]).to(classOf[GroupMembershipImpl])
    bind(classOf[UserRepository]).to(classOf[UserRepositoryImpl])
    bind(classOf[UserSessionRepository]).to(classOf[UserSessionRepositoryImpl])
    bind(classOf[TodoRepository]).to(classOf[TodoRepositoryImpl])
  }
}
