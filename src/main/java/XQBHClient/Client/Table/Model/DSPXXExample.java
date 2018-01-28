package XQBHClient.Client.Table.Model;

import java.util.ArrayList;
import java.util.List;

public class DSPXXExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public DSPXXExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andFRDM_UIsNull() {
            addCriterion("FRDM_U is null");
            return (Criteria) this;
        }

        public Criteria andFRDM_UIsNotNull() {
            addCriterion("FRDM_U is not null");
            return (Criteria) this;
        }

        public Criteria andFRDM_UEqualTo(String value) {
            addCriterion("FRDM_U =", value, "FRDM_U");
            return (Criteria) this;
        }

        public Criteria andFRDM_UNotEqualTo(String value) {
            addCriterion("FRDM_U <>", value, "FRDM_U");
            return (Criteria) this;
        }

        public Criteria andFRDM_UGreaterThan(String value) {
            addCriterion("FRDM_U >", value, "FRDM_U");
            return (Criteria) this;
        }

        public Criteria andFRDM_UGreaterThanOrEqualTo(String value) {
            addCriterion("FRDM_U >=", value, "FRDM_U");
            return (Criteria) this;
        }

        public Criteria andFRDM_ULessThan(String value) {
            addCriterion("FRDM_U <", value, "FRDM_U");
            return (Criteria) this;
        }

        public Criteria andFRDM_ULessThanOrEqualTo(String value) {
            addCriterion("FRDM_U <=", value, "FRDM_U");
            return (Criteria) this;
        }

        public Criteria andFRDM_ULike(String value) {
            addCriterion("FRDM_U like", value, "FRDM_U");
            return (Criteria) this;
        }

        public Criteria andFRDM_UNotLike(String value) {
            addCriterion("FRDM_U not like", value, "FRDM_U");
            return (Criteria) this;
        }

        public Criteria andFRDM_UIn(List<String> values) {
            addCriterion("FRDM_U in", values, "FRDM_U");
            return (Criteria) this;
        }

        public Criteria andFRDM_UNotIn(List<String> values) {
            addCriterion("FRDM_U not in", values, "FRDM_U");
            return (Criteria) this;
        }

        public Criteria andFRDM_UBetween(String value1, String value2) {
            addCriterion("FRDM_U between", value1, value2, "FRDM_U");
            return (Criteria) this;
        }

        public Criteria andFRDM_UNotBetween(String value1, String value2) {
            addCriterion("FRDM_U not between", value1, value2, "FRDM_U");
            return (Criteria) this;
        }

        public Criteria andSPLJ_UIsNull() {
            addCriterion("SPLJ_U is null");
            return (Criteria) this;
        }

        public Criteria andSPLJ_UIsNotNull() {
            addCriterion("SPLJ_U is not null");
            return (Criteria) this;
        }

        public Criteria andSPLJ_UEqualTo(String value) {
            addCriterion("SPLJ_U =", value, "SPLJ_U");
            return (Criteria) this;
        }

        public Criteria andSPLJ_UNotEqualTo(String value) {
            addCriterion("SPLJ_U <>", value, "SPLJ_U");
            return (Criteria) this;
        }

        public Criteria andSPLJ_UGreaterThan(String value) {
            addCriterion("SPLJ_U >", value, "SPLJ_U");
            return (Criteria) this;
        }

        public Criteria andSPLJ_UGreaterThanOrEqualTo(String value) {
            addCriterion("SPLJ_U >=", value, "SPLJ_U");
            return (Criteria) this;
        }

        public Criteria andSPLJ_ULessThan(String value) {
            addCriterion("SPLJ_U <", value, "SPLJ_U");
            return (Criteria) this;
        }

        public Criteria andSPLJ_ULessThanOrEqualTo(String value) {
            addCriterion("SPLJ_U <=", value, "SPLJ_U");
            return (Criteria) this;
        }

        public Criteria andSPLJ_ULike(String value) {
            addCriterion("SPLJ_U like", value, "SPLJ_U");
            return (Criteria) this;
        }

        public Criteria andSPLJ_UNotLike(String value) {
            addCriterion("SPLJ_U not like", value, "SPLJ_U");
            return (Criteria) this;
        }

        public Criteria andSPLJ_UIn(List<String> values) {
            addCriterion("SPLJ_U in", values, "SPLJ_U");
            return (Criteria) this;
        }

        public Criteria andSPLJ_UNotIn(List<String> values) {
            addCriterion("SPLJ_U not in", values, "SPLJ_U");
            return (Criteria) this;
        }

        public Criteria andSPLJ_UBetween(String value1, String value2) {
            addCriterion("SPLJ_U between", value1, value2, "SPLJ_U");
            return (Criteria) this;
        }

        public Criteria andSPLJ_UNotBetween(String value1, String value2) {
            addCriterion("SPLJ_U not between", value1, value2, "SPLJ_U");
            return (Criteria) this;
        }

        public Criteria andSPMC_UIsNull() {
            addCriterion("SPMC_U is null");
            return (Criteria) this;
        }

        public Criteria andSPMC_UIsNotNull() {
            addCriterion("SPMC_U is not null");
            return (Criteria) this;
        }

        public Criteria andSPMC_UEqualTo(String value) {
            addCriterion("SPMC_U =", value, "SPMC_U");
            return (Criteria) this;
        }

        public Criteria andSPMC_UNotEqualTo(String value) {
            addCriterion("SPMC_U <>", value, "SPMC_U");
            return (Criteria) this;
        }

        public Criteria andSPMC_UGreaterThan(String value) {
            addCriterion("SPMC_U >", value, "SPMC_U");
            return (Criteria) this;
        }

        public Criteria andSPMC_UGreaterThanOrEqualTo(String value) {
            addCriterion("SPMC_U >=", value, "SPMC_U");
            return (Criteria) this;
        }

        public Criteria andSPMC_ULessThan(String value) {
            addCriterion("SPMC_U <", value, "SPMC_U");
            return (Criteria) this;
        }

        public Criteria andSPMC_ULessThanOrEqualTo(String value) {
            addCriterion("SPMC_U <=", value, "SPMC_U");
            return (Criteria) this;
        }

        public Criteria andSPMC_ULike(String value) {
            addCriterion("SPMC_U like", value, "SPMC_U");
            return (Criteria) this;
        }

        public Criteria andSPMC_UNotLike(String value) {
            addCriterion("SPMC_U not like", value, "SPMC_U");
            return (Criteria) this;
        }

        public Criteria andSPMC_UIn(List<String> values) {
            addCriterion("SPMC_U in", values, "SPMC_U");
            return (Criteria) this;
        }

        public Criteria andSPMC_UNotIn(List<String> values) {
            addCriterion("SPMC_U not in", values, "SPMC_U");
            return (Criteria) this;
        }

        public Criteria andSPMC_UBetween(String value1, String value2) {
            addCriterion("SPMC_U between", value1, value2, "SPMC_U");
            return (Criteria) this;
        }

        public Criteria andSPMC_UNotBetween(String value1, String value2) {
            addCriterion("SPMC_U not between", value1, value2, "SPMC_U");
            return (Criteria) this;
        }

        public Criteria andSL_UUUIsNull() {
            addCriterion("SL_UUU is null");
            return (Criteria) this;
        }

        public Criteria andSL_UUUIsNotNull() {
            addCriterion("SL_UUU is not null");
            return (Criteria) this;
        }

        public Criteria andSL_UUUEqualTo(Integer value) {
            addCriterion("SL_UUU =", value, "SL_UUU");
            return (Criteria) this;
        }

        public Criteria andSL_UUUNotEqualTo(Integer value) {
            addCriterion("SL_UUU <>", value, "SL_UUU");
            return (Criteria) this;
        }

        public Criteria andSL_UUUGreaterThan(Integer value) {
            addCriterion("SL_UUU >", value, "SL_UUU");
            return (Criteria) this;
        }

        public Criteria andSL_UUUGreaterThanOrEqualTo(Integer value) {
            addCriterion("SL_UUU >=", value, "SL_UUU");
            return (Criteria) this;
        }

        public Criteria andSL_UUULessThan(Integer value) {
            addCriterion("SL_UUU <", value, "SL_UUU");
            return (Criteria) this;
        }

        public Criteria andSL_UUULessThanOrEqualTo(Integer value) {
            addCriterion("SL_UUU <=", value, "SL_UUU");
            return (Criteria) this;
        }

        public Criteria andSL_UUUIn(List<Integer> values) {
            addCriterion("SL_UUU in", values, "SL_UUU");
            return (Criteria) this;
        }

        public Criteria andSL_UUUNotIn(List<Integer> values) {
            addCriterion("SL_UUU not in", values, "SL_UUU");
            return (Criteria) this;
        }

        public Criteria andSL_UUUBetween(Integer value1, Integer value2) {
            addCriterion("SL_UUU between", value1, value2, "SL_UUU");
            return (Criteria) this;
        }

        public Criteria andSL_UUUNotBetween(Integer value1, Integer value2) {
            addCriterion("SL_UUU not between", value1, value2, "SL_UUU");
            return (Criteria) this;
        }

        public Criteria andJLZT_UIsNull() {
            addCriterion("JLZT_U is null");
            return (Criteria) this;
        }

        public Criteria andJLZT_UIsNotNull() {
            addCriterion("JLZT_U is not null");
            return (Criteria) this;
        }

        public Criteria andJLZT_UEqualTo(String value) {
            addCriterion("JLZT_U =", value, "JLZT_U");
            return (Criteria) this;
        }

        public Criteria andJLZT_UNotEqualTo(String value) {
            addCriterion("JLZT_U <>", value, "JLZT_U");
            return (Criteria) this;
        }

        public Criteria andJLZT_UGreaterThan(String value) {
            addCriterion("JLZT_U >", value, "JLZT_U");
            return (Criteria) this;
        }

        public Criteria andJLZT_UGreaterThanOrEqualTo(String value) {
            addCriterion("JLZT_U >=", value, "JLZT_U");
            return (Criteria) this;
        }

        public Criteria andJLZT_ULessThan(String value) {
            addCriterion("JLZT_U <", value, "JLZT_U");
            return (Criteria) this;
        }

        public Criteria andJLZT_ULessThanOrEqualTo(String value) {
            addCriterion("JLZT_U <=", value, "JLZT_U");
            return (Criteria) this;
        }

        public Criteria andJLZT_ULike(String value) {
            addCriterion("JLZT_U like", value, "JLZT_U");
            return (Criteria) this;
        }

        public Criteria andJLZT_UNotLike(String value) {
            addCriterion("JLZT_U not like", value, "JLZT_U");
            return (Criteria) this;
        }

        public Criteria andJLZT_UIn(List<String> values) {
            addCriterion("JLZT_U in", values, "JLZT_U");
            return (Criteria) this;
        }

        public Criteria andJLZT_UNotIn(List<String> values) {
            addCriterion("JLZT_U not in", values, "JLZT_U");
            return (Criteria) this;
        }

        public Criteria andJLZT_UBetween(String value1, String value2) {
            addCriterion("JLZT_U between", value1, value2, "JLZT_U");
            return (Criteria) this;
        }

        public Criteria andJLZT_UNotBetween(String value1, String value2) {
            addCriterion("JLZT_U not between", value1, value2, "JLZT_U");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}