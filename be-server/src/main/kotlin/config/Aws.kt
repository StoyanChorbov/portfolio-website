package config

import aws.sdk.kotlin.runtime.auth.credentials.StaticCredentialsProvider
import aws.sdk.kotlin.services.s3.S3Client
import aws.smithy.kotlin.runtime.net.url.Url

object Aws {
    private val config = System.getenv()
    val publicS3Url: String = System.getenv("AWS_ENDPOINT_URL_S3")

    val bucket: String = config.getValue("S3_BUCKET")
    val s3Client = S3Client {
        region = System.getenv("AWS_REGION")
        endpointUrl = Url.parse(
            publicS3Url
        )
        forcePathStyle = true
        credentialsProvider = StaticCredentialsProvider {
            accessKeyId = config.getValue("AWS_ACCESS_KEY_ID")
            secretAccessKey = config.getValue("AWS_SECRET_ACCESS_KEY")
        }
    }
}