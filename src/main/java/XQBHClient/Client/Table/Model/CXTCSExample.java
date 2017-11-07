package XQBHClient.Client.Table.Model;

import java.util.ArrayList;
import java.util.List;

public class CXTCSExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CXTCSExample() {
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

        public Criteria andKEY_UUIsNull() {
            addCriterion("KEY_UU is null");
            return (Criteria) this;
        }

        public Criteria andKEY_UUIsNotNull() {
            addCriterion("KEY_UU is not null");
            return (Criteria) this;
        }

        public Criteria andKEY_UUEqualTo(String value) {
            addCriterion("KEY_UU =", value, "KEY_UU");
            return (Criteria) this;
        }

        public Criteria andKEY_UUNotEqualTo(String value) {
            addCriterion("KEY_UU <>", value, "KEY_UU");
            return (Criteria) this;
        }

        public Criteria andKEY_UUGreaterThan(String value) {
            addCriterion("KEY_UU >", value, "KEY_UU");
            return (Criteria) this;
        }

        public Criteria andKEY_UUGreaterThanOrEqualTo(String value) {
            addCriterion("KEY_UU >=", value, "KEY_UU");
            return (Criteria) this;
        }

        public Criteria andKEY_UULessThan(String value) {
            addCriterion("KEY_UU <", value, "KEY_UU");
            return (Criteria) this;
        }

        public Criteria andKEY_UULessThanOrEqualTo(String value) {
            addCriterion("KEY_UU <=", value, "KEY_UU");
            return (Criteria) this;
        }

        public Criteria andKEY_UULike(String value) {
            addCriterion("KEY_UU like", value, "KEY_UU");
            return (Criteria) this;
        }

        public Criteria andKEY_UUNotLike(String value) {
            addCriterion("KEY_UU not like", value, "KEY_UU");
            return (Criteria) this;
        }

        public Criteria andKEY_UUIn(List<String> values) {
            addCriterion("KEY_UU in", values, "KEY_UU");
            return (Criteria) this;
        }

        public Criteria andKEY_UUNotIn(List<String> values) {
            addCriterion("KEY_UU not in", values, "KEY_UU");
            return (Criteria) this;
        }

        public Criteria andKEY_UUBetween(String value1, String value2) {
            addCriterion("KEY_UU between", value1, value2, "KEY_UU");
            return (Criteria) this;
        }

        public Criteria andKEY_UUNotBetween(String value1, String value2) {
            addCriterion("KEY_UU not between", value1, value2, "KEY_UU");
            return (Criteria) this;
        }

        public Criteria andVALUE_IsNull() {
            addCriterion("VALUE_ is null");
            return (Criteria) this;
        }

        public Criteria andVALUE_IsNotNull() {
            addCriterion("VALUE_ is not null");
            return (Criteria) this;
        }

        public Criteria andVALUE_EqualTo(String value) {
            addCriterion("VALUE_ =", value, "VALUE_");
            return (Criteria) this;
        }

        public Criteria andVALUE_NotEqualTo(String value) {
            addCriterion("VALUE_ <>", value, "VALUE_");
            return (Criteria) this;
        }

        public Criteria andVALUE_GreaterThan(String value) {
            addCriterion("VALUE_ >", value, "VALUE_");
            return (Criteria) this;
        }

        public Criteria andVALUE_GreaterThanOrEqualTo(String value) {
            addCriterion("VALUE_ >=", value, "VALUE_");
            return (Criteria) this;
        }

        public Criteria andVALUE_LessThan(String value) {
            addCriterion("VALUE_ <", value, "VALUE_");
            return (Criteria) this;
        }

        public Criteria andVALUE_LessThanOrEqualTo(String value) {
            addCriterion("VALUE_ <=", value, "VALUE_");
            return (Criteria) this;
        }

        public Criteria andVALUE_Like(String value) {
            addCriterion("VALUE_ like", value, "VALUE_");
            return (Criteria) this;
        }

        public Criteria andVALUE_NotLike(String value) {
            addCriterion("VALUE_ not like", value, "VALUE_");
            return (Criteria) this;
        }

        public Criteria andVALUE_In(List<String> values) {
            addCriterion("VALUE_ in", values, "VALUE_");
            return (Criteria) this;
        }

        public Criteria andVALUE_NotIn(List<String> values) {
            addCriterion("VALUE_ not in", values, "VALUE_");
            return (Criteria) this;
        }

        public Criteria andVALUE_Between(String value1, String value2) {
            addCriterion("VALUE_ between", value1, value2, "VALUE_");
            return (Criteria) this;
        }

        public Criteria andVALUE_NotBetween(String value1, String value2) {
            addCriterion("VALUE_ not between", value1, value2, "VALUE_");
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