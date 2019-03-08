package com.app.roadsafety.intractor.createincident;

import com.app.roadsafety.frameworks.retrofit.ResponseResolver;
import com.app.roadsafety.frameworks.retrofit.RestError;
import com.app.roadsafety.frameworks.retrofit.WebServicesWrapper;
import com.app.roadsafety.model.cityhall.CityHallResponse;
import com.app.roadsafety.model.createIncident.CreateIncidentRequest;
import com.app.roadsafety.model.createIncident.CreateIncidentResponse;
import com.app.roadsafety.model.createIncident.ReportAbuseIncidentRequest;
import com.app.roadsafety.model.createIncident.ReportAbuseIncidentResponse;
import com.google.gson.Gson;

import retrofit2.Response;

public class CreateIncidentntractorImpl implements ICreateIncidentIntractor {

    @Override
    public void getCityHall(final OnFinishedListener listener) {
        try {
            WebServicesWrapper.getInstance().getCityHall(new ResponseResolver<CityHallResponse>() {
                @Override
                public void onSuccess(CityHallResponse loginResponse, Response response) {
                    listener.onSuccessCityHallResponse(loginResponse);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if (error == null || error.getError() == null) {
                        try {
                            Gson gson = new Gson();
                            CityHallResponse response = gson.fromJson(msg, CityHallResponse.class);
                            listener.onSuccessCityHallResponse(response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        listener.onError(error.getError());
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createIncident(String auth_token, CreateIncidentRequest createIncidentRequest, final OnFinishedListener listener) {
        try {
            WebServicesWrapper.getInstance().createIncident(new ResponseResolver<CreateIncidentResponse>() {
                @Override
                public void onSuccess(CreateIncidentResponse loginResponse, Response response) {
                    listener.onSuccessCreateIncidentResponse(loginResponse);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if (error == null || error.getError() == null) {
                        try {
                            Gson gson = new Gson();
                            CreateIncidentResponse response = gson.fromJson(msg, CreateIncidentResponse.class);
                            listener.onSuccessCreateIncidentResponse(response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        listener.onError(error.getError());
                    }
                }
            }, auth_token,createIncidentRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateIncident(String auth_token, String id, CreateIncidentRequest createIncidentRequest,final OnFinishedListener listener) {
        try {
            WebServicesWrapper.getInstance().updateIncident(new ResponseResolver<CreateIncidentResponse>() {
                @Override
                public void onSuccess(CreateIncidentResponse loginResponse, Response response) {
                    listener.onSuccessCreateIncidentResponse(loginResponse);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if (error == null || error.getError() == null) {
                        try {
                            Gson gson = new Gson();
                            CreateIncidentResponse response = gson.fromJson(msg, CreateIncidentResponse.class);
                            listener.onSuccessCreateIncidentResponse(response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        listener.onError(error.getError());
                    }
                }
            }, auth_token,id,createIncidentRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteIncident(String auth_token, String id, final OnFinishedListener listener) {
        try {
            WebServicesWrapper.getInstance().deleteIncident(new ResponseResolver<CreateIncidentResponse>() {
                @Override
                public void onSuccess(CreateIncidentResponse loginResponse, Response response) {
                    listener.onSuccessCreateIncidentResponse(loginResponse);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if (error == null || error.getError() == null) {
                        try {
                            Gson gson = new Gson();
                            CreateIncidentResponse response = gson.fromJson(msg, CreateIncidentResponse.class);
                            listener.onSuccessCreateIncidentResponse(response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        listener.onError(error.getError());
                    }
                }
            }, auth_token,id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void reportAbuseIncident(String auth_token, String id, ReportAbuseIncidentRequest reportAbuseIncidentRequest, final OnFinishedListener listener) {
        try {
            WebServicesWrapper.getInstance().reportAbuseIncident(new ResponseResolver<ReportAbuseIncidentResponse>() {
                @Override
                public void onSuccess(ReportAbuseIncidentResponse loginResponse, Response response) {
                    listener.onSuccessReportAbuseIncidentResponse(loginResponse);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if (error == null || error.getError() == null) {
                        try {
                            Gson gson = new Gson();
                            ReportAbuseIncidentResponse response = gson.fromJson(msg, ReportAbuseIncidentResponse.class);
                            listener.onSuccessReportAbuseIncidentResponse(response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        listener.onError(error.getError());
                    }
                }
            }, auth_token,id,reportAbuseIncidentRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
