import com.google.inject.AbstractModule
import domain.repository.TodoRepository
import infrastructure.repositoryimpl.TodoRepositoryImpl

class Module extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[TodoRepository]).to(classOf[TodoRepositoryImpl])
  }
}
