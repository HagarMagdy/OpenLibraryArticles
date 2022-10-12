package com.example.openlibraryarticles.utills

interface EntityMapper<Entity,DomainEntity> {
    fun mapFromEntity(entity: Entity): DomainEntity

    fun mapToEntity(domainEntity: DomainEntity): Entity

}