package com.example.testapplication.room.converter

import com.example.testapplication.retrofit.datamodel.Repository
import com.example.testapplication.room.repository.RepositoryEntity

class RepositoryToRepositoryEntityConverter {

    companion object{
        fun convert(repo: Repository):RepositoryEntity{
            return RepositoryEntity(0,name = repo.name,
                language = repo.language,
                avatar_url = repo.owner.avatar_url,
                forks = repo.forks_count,
                description = repo.description,
                html_url = repo.html_url,
                watch = repo.watchers_count)
        }

    }



}