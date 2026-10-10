package service

import aws.smithy.kotlin.runtime.content.asByteStream
import config.Aws
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.nio.file.Files

class FileService(private val awsService: AwsService) {
    suspend fun uploadFile(file: File, path: String): String {
        val bytes = file.asByteStream()
        awsService.UploadFile(path, bytes, contentType = withContext(Dispatchers.IO) {
            Files.probeContentType(file.toPath())
        })
        return "${Aws.publicS3Url}/${Aws.bucket}/${path}"
    }
}