package repository

import exception.NullStringPropertyException
import model.ProjectDTO
import model.Projects
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.deleteWhere
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.suspendTransaction
import org.jetbrains.exposed.v1.jdbc.update
import kotlin.uuid.Uuid

class ProjectsRepository(private val database: Database) {
    suspend fun getAllProjects(): List<ProjectDTO> =
        suspendTransaction(database) {
            Projects
                .selectAll()
                .map {
                    ProjectDTO(
                        name = it[Projects.name],
                        description = it[Projects.description],
                        url = it[Projects.url],
                        imageUrl = it[Projects.imageUrl],
                    )
                }
        }

    suspend fun getProject(id: Uuid): ProjectDTO? =
        suspendTransaction(database) {
            Projects
                .selectAll()
                .where { Projects.id eq id }
                .map {
                    ProjectDTO(
                        name = it[Projects.name],
                        description = it[Projects.description],
                        url = it[Projects.url],
                        imageUrl = it[Projects.imageUrl],
                    )
                }
                .singleOrNull()
        }

    suspend fun createProject(project: ProjectDTO): Uuid =
        suspendTransaction(database) {
            if (project.name == null)
                throw NullStringPropertyException("Project name can't be null!")
            if (project.description == null)
                throw NullStringPropertyException("Project description can't be null!")
            val newProject = Projects.insert {
                it[name] = project.name
                it[description] = project.description
                it[url] = project.url
            }
            newProject[Projects.id].value
        }

    suspend fun updateProject(id: Uuid, updatedProject: ProjectDTO) =
        suspendTransaction(database) {
            Projects.update({ Projects.id eq id }) {
                if (updatedProject.name != null)
                    it[name] = updatedProject.name
                if (updatedProject.description != null)
                    it[description] = updatedProject.description
                if (updatedProject.url != null)
                it[url] = updatedProject.url
                if (updatedProject.imageUrl != null)
                it[imageUrl] = updatedProject.imageUrl
            }
        }

    suspend fun deleteProject(id: Uuid) =
        suspendTransaction(database) {
            Projects.deleteWhere { Projects.id eq id }
        }
}