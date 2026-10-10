package service

import model.ProjectDTO
import repository.ProjectsRepository
import java.io.File
import kotlin.uuid.Uuid

class ProjectsService(private val repository: ProjectsRepository, private val fileService: FileService) {
    suspend fun getAll() =
        repository.getAllProjects()

    suspend fun getProject(id: Uuid) =
        repository.getProject(id)

    suspend fun createProject(project: ProjectDTO, image: File?): Uuid {
        val projectId = repository.createProject(project)

        if (image != null) {
            val imageUrl = fileService.uploadFile(file = image, "projects/${projectId}/${Uuid.random()}")
            repository.updateProject(
                id = projectId,
                updatedProject = ProjectDTO(
                    name = project.name,
                    description = project.description,
                    url = project.url,
                    imageUrl = imageUrl
                )
            )
        }

        return projectId
    }

    suspend fun updateProject(id: Uuid, updatedProject: ProjectDTO, image: File?) {
        if (image == null) {
            repository.updateProject(id, updatedProject)
            return
        }
        val imageUrl: String = fileService.uploadFile(image, path = "projects/${id}/${Uuid.random()}")
        repository.updateProject(
            id,
            ProjectDTO(
                name = updatedProject.name,
                description = updatedProject.description,
                url = updatedProject.url,
                imageUrl = imageUrl
            )
        )
    }

    suspend fun deleteProject(id: Uuid) =
        repository.deleteProject(id)
}