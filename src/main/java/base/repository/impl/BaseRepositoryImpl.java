package base.repository.impl;

import base.entity.BaseEntity;
import base.repository.BaseRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.io.Serializable;
import java.util.List;


public abstract class BaseRepositoryImpl<E extends BaseEntity> implements BaseRepository<E> {

        public abstract Class<E> getEntityClass();
        protected final EntityManager em;

        public BaseRepositoryImpl(EntityManager em){
            this.em=em;
        }
        @Override
        public E save(E e) {
            em.persist(e);
            return e;
        }

        @Override
        public E update(E e) {
            em.merge(e);
            return e;
        }

        @Override
        public void delete(E e) {
            em.remove(e);
        }

        @Override
        public E findById(Long id) {
            return em.find(getEntityClass(),id);
        }

        @Override
        public List<E> findAll() {
            return em.createQuery("from "+getEntityClass().getSimpleName(),getEntityClass())
                    .getResultList();
        }

        @Override
        public boolean isExistsById(Long id) {
            TypedQuery<Long> query = em.createQuery("select count (id) from " + getEntityClass().getSimpleName() + " where id= :pk ", Long.class);
            Long count = query.setParameter("pk", id).getSingleResult();
            return count==1;
        }

        @Override
        public Long countAll() {
            return em.createQuery("select count (id) from "+getEntityClass().getSimpleName(),Long.class).getSingleResult();
        }

        @Override
        public EntityManager getEntityManager() {
            return em;
        }
    }


