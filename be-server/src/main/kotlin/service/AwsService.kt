package service

import aws.sdk.kotlin.services.s3.S3Client
import aws.sdk.kotlin.services.s3.model.PutObjectRequest
import aws.smithy.kotlin.runtime.content.ByteStream

class AwsService(private val s3Client: S3Client, private val s3Bucket: String) {
    suspend fun UploadFile(key: String, data: ByteStream, contentType: String) {
        val response = s3Client.putObject(
            PutObjectRequest {
                bucket = s3Bucket
                this.key = key
                body = data
                this.contentType = contentType
            }
        )
    }
}