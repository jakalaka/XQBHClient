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

public class SetGoodsAccountToModel {
    public static void exec(DataModel dataModel) throws IOException {
        DBAccess dbAccess = new DBAccess();
        SqlSession sqlSession = null;
        sqlSession = dbAccess.getSqlSession();
        DSPXXExample dspxxExample = new DSPXXExample();
        dspxxExample.or().andJLZT_UEqualTo("0");
        List<DSPXX> dspxxList = new ArrayList();
        DSPXXMapper dspxxMapper = sqlSession.getMapper(DSPXXMapper.class);
        dspxxList = dspxxMapper.selectByExample(dspxxExample);
        for (DSPXX dspxx:dspxxList)
        {
            DataModel.getDataModelByKey(dataModel,dspxx.getSPLJ_U()).setGoodsAmount(dspxx.getSL_UUU());
        }
    }
}
