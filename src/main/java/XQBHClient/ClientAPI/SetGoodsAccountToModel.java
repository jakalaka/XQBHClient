package XQBHClient.ClientAPI;

import XQBHClient.Client.Com;
import XQBHClient.Client.Table.Mapper.DSPXXMapper;
import XQBHClient.Client.Table.Model.DSPXX;
import XQBHClient.Client.Table.Model.DSPXXExample;
import XQBHClient.Client.Table.basic.DBAccess;
import XQBHClient.Utils.Model.DataModel;
import XQBHClient.Utils.log.Logger;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SetGoodsAccountToModel {
    public static void exec(DataModel dataModel) throws IOException {
        List<String> goodsList=DataModel.getGoodsList(dataModel);
        if (goodsList.size()==0) {
            Logger.log("LOG_DEBUG","not search ");
            return;
        }
        DBAccess dbAccess = new DBAccess();
        SqlSession sqlSession = null;
        sqlSession = dbAccess.getSqlSession();
        DSPXXExample dspxxExample = new DSPXXExample();
        dspxxExample.or().andJLZT_UEqualTo("0").andSPLJ_UIn(goodsList);
        List<DSPXX> dspxxList = new ArrayList();
        DSPXXMapper dspxxMapper = sqlSession.getMapper(DSPXXMapper.class);
        dspxxList = dspxxMapper.selectByExample(dspxxExample);
        for (DSPXX dspxx:dspxxList)
        {
            DataModel.getDataModelByKey(dataModel,dspxx.getSPLJ_U()).setGoodsAmount(dspxx.getSL_UUU());
        }
    }
    public static void execAll(DataModel dataModel) throws IOException {

        List<String> goodsList=DataModel.getAllGoodsList(dataModel);
        if (goodsList.size()==0)
        {
            Logger.log("LOG_DEBUG","not search ");
            return;
        }

        DBAccess dbAccess = new DBAccess();
        SqlSession sqlSession = null;
        sqlSession = dbAccess.getSqlSession();
        DSPXXExample dspxxExample = new DSPXXExample();

        dspxxExample.or().andJLZT_UEqualTo("0").andSPLJ_UIn(goodsList);
        List<DSPXX> dspxxList = new ArrayList();
        DSPXXMapper dspxxMapper = sqlSession.getMapper(DSPXXMapper.class);
        dspxxList = dspxxMapper.selectByExample(dspxxExample);
        for (DSPXX dspxx:dspxxList)
        {
            DataModel.getDataModelByKey(dataModel,dspxx.getSPLJ_U()).setGoodsAmount(dspxx.getSL_UUU());
        }
    }
}
