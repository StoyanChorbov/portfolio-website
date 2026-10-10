package config

import org.jetbrains.exposed.v1.jdbc.Database
import repository.ProjectsRepository
import org.koin.dsl.module
import org.koin.plugin.module.dsl.single
import service.AwsService
import service.FileService
import service.ProjectsService

val appModule = module {
    // Database
    single<Database> {
        Database.connect(
            url = System.getenv("DB_URL"),
            user = System.getenv("DB_USER"),
            password = System.getenv("DB_PASS"),
        )
    }

    //Repositories
    single<ProjectsRepository>()

    // Services
    single<AwsService> {
        AwsService(
            s3Client = Aws.s3Client,
            s3Bucket = Aws.bucket
        )
    }
    single<FileService> {
        FileService(awsService = get())
    }
    single<ProjectsService>()
}